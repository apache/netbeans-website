/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
'use strict';

const gulp          = require('gulp'),
      fs            = require('fs'),
      del           = require('del'),
      sass          = require('gulp-sass'),
      cssmin        = require('gulp-cssnano'),
      rename        = require('gulp-rename'),
      sourcemaps    = require('gulp-sourcemaps'),
      header        = require('gulp-header');

const buildDir = '../../../build/bake/';

gulp.task('styles', function() {
    gulp.src('css/font-awesome.min.css').pipe(gulp.dest(buildDir + 'css'));

    return gulp.src('scss/netbeans.scss')
        .pipe(sourcemaps.init())
        .pipe(sass().on('error', sass.logError))
        .pipe(cssmin())
        .pipe(rename('netbeans.css'))
//        .pipe(rename('styles.min.css'))
        .pipe(header('/**\n' + fs.readFileSync('LICENSE', 'utf8') + '*/\n'))
        .pipe(sourcemaps.write('.'))
        .pipe(gulp.dest(buildDir + 'css'));
});

gulp.task('js', function() {
    return gulp.src('js/**/*').pipe(gulp.dest(buildDir + 'js'));
});

gulp.task('fonts', function() {
    return gulp.src('fonts/**/*').pipe(gulp.dest(buildDir + 'fonts'));
});

gulp.task('clean', function() {
    return del([
        buildDir + 'css',
        buildDir + 'fonts',
        buildDir + 'js'
    ], {force: true});
});

gulp.task('watch', function() {
    gulp.watch('scss/**/*.scss', ['styles']);
});

gulp.task('build', gulp.series('clean', 'styles', 'js', 'fonts'));
gulp.task('default', gulp.series('build'));
