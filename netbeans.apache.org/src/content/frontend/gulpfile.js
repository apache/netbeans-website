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
