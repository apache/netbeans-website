/*
 * This is a multibranch pipeline that:
 * - Checks out (shallow, read-only) the 'master' branch from netbeans-website (github) in the 'master-branch' directory.
 * - Checks out the 'asf-site' branch from netbeans-website (gitbox) in the 'asf-site-branch' directory.
 * - Runs a gradle job to build the website in the 'master-branch' directory.
 * - Copies the resulting web stuff (html, images, etc.) to the 'asf-site-branch' directory.
 * - Commits and pushes to the 'asf-site' branch, for later publishing.
 *
 * Questions and enhancements to dev@netbeans.apache.org, ebarboni@apache.org or vieiro@apache.org.
 *
 * Note from vieiro@apache.org: That what once was a simple shell script is now a nightmare of complexity and a waste of computing resources.
 *
 * Useful references:
 * - The pipeline DSL nightmare reference is available at https://www.jenkins.io/doc/book/pipeline/syntax/
 * - 
 */
pipeline {
    // 'git-websites' as set by ASF Infra. This is the label used to build websites.
    agent { node { label 'git-websites' } }
    tools { jdk 'jdk_11_latest' }
    triggers {
        pollSCM('') // Enabling being build on Push
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '6'))
        disableConcurrentBuilds() 
        timeout(time: 50, unit: 'MINUTES')
    }
    stages {
        stage("Shallow clone of github master branch") {
            steps {
                script {
                    def masterBranch = checkout([
                            $class: 'GitSCM', 
                            branches: [[name: '*/master']], 
                            doGenerateSubmoduleConfigurations: false, 
                            extensions: [
                                [$class: 'MessageExclusion', excludedMessage: 'Automated site publishing.*'], 
                                [$class: 'CloneOption', depth: 1, noTags: true, reference: '', shallow: true], 
                                [$class: 'RelativeTargetDirectory', relativeTargetDir: 'master-branch']
                            ], 
                            submoduleCfg: [], 
                            userRemoteConfigs: [[url: 'https://github.com/apache/netbeans-website.git']]
                        ])
                    def siteBranch   = checkout([
                            $class: 'GitSCM', 
                            branches: [[name: '*/asf-site']], 
                            doGenerateSubmoduleConfigurations: false, 
                            extensions: [
                                [$class: 'MessageExclusion', excludedMessage: 'Automated site publishing.*'],
                                [$class: 'RelativeTargetDirectory', relativeTargetDir: 'asf-site-branch']
                            ], 
                            submoduleCfg: [], 
                            userRemoteConfigs: [[
                                    credentialsId: '9b041bd0-aea9-4498-a576-9eeb771411dd',
                                    url: 'https://gitbox.apache.org/repos/asf/netbeans-website.git'
                                ]]
                        ])
                    def gitCommit = masterBranch.GIT_COMMIT
                    def gitPreviousCommit = masterBranch.GIT_PREVIOUS_COMMIT
                    println("GIT_COMMIT:          ${gitCommit}")
                    println("GIT_PREVIOUS_COMMIT: ${gitPreviousCommit}")
                    println("MASTER BRANCH      : ${masterBranch}")
                    // We want to rebuild the website when 'master' changes, not when 'asf-site' changes...
                    if (gitCommit.equals(gitPreviousCommit)) {
                        // vieiro/2020-07-25
                        // It seems there's no clean way to stop a build from a step without using exceptions and weird things.
                        // It seems "As a user I want to stop a build from a stage step" is not a user story jenkins authors had in mind.
                        // This is very unfortunate and has caused pain all around the world.
                        // https://devops.stackexchange.com/questions/885/cleanest-way-to-prematurely-exit-a-jenkins-pipeline-job-as-a-success
                        currentBuild.result = 'ABORTED'
                        error("Commit ${gitCommit} and previous commit ${gitPreviousCommit} are equal. No need to rebuild the website.")
                    } else {
                        println("Different commits, proceeding...")
                    }
                }
                dir('asf-site-branch') {
                    sh 'git fetch'
                    sh 'git checkout asf-site'
                    sh 'git pull'
                    sh 'git status'
                }
            }
        }
        stage("Building the website") {
            steps {
                dir('master-branch/netbeans.apache.org') {
                    sh 'chmod u+x ./gradlew'
                    sh './gradlew --version'
                    sh './gradlew --stop'
                    sh './gradlew --no-daemon clean buildSite'
                }
            }
        }
        stage("Updating asf-site branch") {
            steps {
                dir('asf-site-branch') {
                    sh script: '''
#!/usr/bin/env bash

echo "Deleting content directory..."
rm -rf content/
mkdir content
echo "Copying HTML files..."
cp -R ../master-branch/netbeans.apache.org/build/bake/* content
echo "Copying .htaccess..."
cp -R ../master-branch/netbeans.apache.org/src/content/.htaccess content
echo "Copying wiki/.htaccess..."
cp -R ../master-branch/netbeans.apache.org/src/content/wiki/.htaccess content/wiki
echo "All files copied"
cp  ../master-branch/.asf.yaml .
echo "Copying asf yaml file to publish branch"

'''
                }
            }
        }
        stage("Commit and push to asf-site") {
            steps {
                dir('asf-site-branch') {
                    sh script: '''
#!/usr/bin/env bash
echo "Checking git status"
git status
echo "Adding content..."
git add -v content/
echo "Adding asf file"
git add -v .asf.yaml
echo "Commit to gitbox..."
git status
git commit -v -m "Automated site publishing by Jenkins build ${BUILD_NUMBER}"
if [ $? -ne 0 ]; then
    echo "Commit failed."
    exit 2
fi
echo "Pushing to gitbox... Disabled for testing"
git push -v origin asf-site
if [ $? -ne 0 ]; then
    echo "Push failed."
    exit 3
fi
echo "Done."
'''
                }
            }
        }
    }
    post {
        cleanup {
            println "Cleanup todo (using cleanWS to test)"
            cleanWs()
        }
        success {
            //echo "Success?"
            slackSend (channel:'#netbeans-builds', message:"SUCCESS: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]' (${env.BUILD_URL}) ",color:'#00FF00')
        }
        failure {
            //echo "Failure?"
            slackSend (channel:'#netbeans-builds', message:"FAILED/ABORTED: Job '${env.JOB_NAME} [${env.BUILD_NUMBER}]'  (${env.BUILD_URL})",color:'#FF0000')
        }
    }
}
