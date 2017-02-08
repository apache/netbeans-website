(function () {
    'use strict';

    var burgerMenu = document.querySelector('#burger-menu'),
        links = [].slice.call(document.querySelectorAll('.nav-link')),
    
        body = document.querySelector('body'),

        addClass = function (elem, className) {
            if (elem.className.indexOf(className) === -1) {
                elem.className += ' ' + className;
            }
        },

        removeClass = function (elem, className) {
            var classes = elem.className.replace(' ' + className, '');
            elem.className = classes;
        },

        toggle = function (elem, expr, className) {
            if (expr) {
                addClass(elem, className);
            } else {
                removeClass(elem, className);
            }
        },

        setPushStateUrl = function (target) {
            window.history.pushState(null, target.text, target);
        };
        
        if (!window.location.hostname === 'localhost') {
            var path = window.location.pathname.replace('/', '');

            if (path) {
                window.scrollTo(0, document.querySelector('#' + path).offsetTop);
            }
        }

    [].slice.call(document.querySelectorAll('.ripple-btn')).forEach(function (btn) {
        btn.addEventListener('mousedown', function (event) {
            var ripple = this.querySelector('.ripple');

            addClass(ripple, 'visible');
            ripple.style.transform = 'translate(-50%, -50%) translate(' + event.layerX + 'px, ' + event.layerY + 'px) scale(0.0001, 0.0001)';

            setTimeout(function () {
                ripple.style.transform = ripple.style.transform.replace(' scale(0.0001, 0.0001)', '');
            }, 200);
        }, true);

        btn.addEventListener('mouseup', function () {
            var ripple = this.querySelector('.ripple');

            removeClass(ripple, 'visible');
        }, true);
    });

    links.forEach(function (link) {
        link.addEventListener('click', function (e) {
            var targetSection = document.querySelector('#' + this.getAttribute('href')),
                endPos = targetSection.offsetTop,
                startPos = window.scrollY;

            e.preventDefault();

            window.removeEventListener('scroll', onScrollMethod);

            setPushStateUrl(this);

            if (startPos <= endPos) {
                animForward(startPos, endPos);
            } else {
                animBackward(startPos, endPos);
            }

            window.addEventListener('scroll', onScrollMethod);
        }, true);
    });
    
//    document.querySelector('div.search').addEventListener('click', function() {
//        var searchField = document.querySelector('.search-field');
//        
//        toggle(searchField, searchField.className.indexOf('active') === -1 , 'active');
//    });

    var animForward = function (startPos, endPos) {
        var incrementer = .1,
            step = function () {
                var diffEndStart = (endPos - startPos);

                incrementer += 1.25;

                if (diffEndStart < 40) {
                    startPos += diffEndStart;
                } else {
                    startPos += (1 * incrementer);
                }

                window.scrollTo(0, startPos);

                if (startPos < endPos) {
                    window.requestAnimationFrame(step);
                }
            };

        window.requestAnimationFrame(step);
    };

    var animBackward = function (startPos, endPos) {
        var incrementer = .1,
            step = function () {
                var diffEndStart = (startPos - endPos);

                incrementer += 1.25;

                if (diffEndStart < 40) {
                    startPos -= diffEndStart;
                } else {
                    startPos -= (1 * incrementer);
                }

                window.scrollTo(0, startPos);

                if (startPos > endPos) {
                    window.requestAnimationFrame(step);
                }
            };

        window.requestAnimationFrame(step);
    };

    document.querySelector('.back-to-top').addEventListener('click', function () {
        window.history.replaceState(null, null, '/');

        animBackward(window.scrollY, 0);
    }, true);

    burgerMenu.addEventListener('click', function () {
        if (body.className.indexOf('active') === -1) {
            addClass(body, 'active');
        } else {
            removeClass(body, 'active');

            body.removeEventListener('click', this);
        }
    }, true);

    var onScrollMethod = function () {
        toggle(document.querySelector('header'), document.body.scrollTop >= 100, 'active');
        toggle(document.querySelector('body'), document.body.scrollTop >= 280, 'scrolled');
    };

    window.addEventListener('scroll', onScrollMethod);
}());
