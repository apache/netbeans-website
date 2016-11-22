/* global Q */

(function () {
    'use strict';

    var validators = [],
        validatorObject = {
            isNotEmpty: function (field) {
                var result = Q.defer(),
                    isValid = (field.value !== '');

                result.resolve({
                    isValid: isValid,
                    errorMessage: isValid ? '' : 'Bitte ' + field.getAttribute('data-placeholder') + ' angeben.'
                });

                return result.promise;
            },

            isMailValid: function (field) {
                var result = Q.defer(),
                    isValid = (/^[_a-zA-Z0-9-]+(\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\.[a-zA-Z0-9-]+)*(\.[a-zA-Z]{2,4})$/).test(field.value);

                result.resolve({
                    isValid: isValid,
                    errorMessage: isValid ? '' : 'Bitte gib eine gültige E-Mailadresse an.'
                });

                return result.promise;
            },

            isCaptchaValid: function () {
                var result = Q.defer();

                result.resolve({
                    isValid: true,
                    errorMessage: ''
                });

                return result.promise;
            }
        },

        isFieldValidatorValid = function (field) {
            return Q.all(validators.map(function(validator) {
                var result = Q.defer();
                
                validator(field).then(function(val) {
                    if (!val.isValid) {
                        result.resolve({
                            isValid: false,
                            errorMessage: val.errorMessage
                        });
                    }
                    
                    result.resolve({
                        isValid: true,
                        errorMessage: ''
                    });
                });

                return result.promise;
            }));
        },

        showError = function (validationField, errorMsg) {
            validationField.querySelector('.error-message').innerText = errorMsg;
            removeClass(validationField, 'mf-valid');
            addClass(validationField, 'mf-error');
        },

        hideError = function (validationField) {
            removeClass(validationField, 'mf-error');
            addClass(validationField, 'mf-valid');
        },

        validateField = function (field) {
            validators.length = 0;

            field.getAttribute('data-validators').split(' ').forEach(function (validator) {
                validators.push(validatorObject[validator]);
            });
            
            var prom = Q.defer();
            
            isFieldValidatorValid(field).then(function(results) {
                var invalidField = results.find(function(elem) {
                    elem.isValid ? hideError(getParents(field, '.validation-field')) : showError(getParents(field, '.validation-field'), elem.errorMessage);
                    
                    return !elem.isValid;
                });
                
                prom.resolve(invalidField ? invalidField.isValid : true);
            });
            
            return prom.promise;
        },

        checkForm = function () {
            var activeFieldset = document.querySelector('#multi-form fieldset.active'),
                isFormValidResult = Q.defer();
        
            if (activeFieldset) {
                var validatableFields = [].slice.call(activeFieldset.querySelectorAll('li.opened .validation-field [data-validators], div.opened .validation-field [data-validators]'));
                
                Q.all(validatableFields.map(function(validatableField) {
                   return validateField(validatableField);
                })).done(function(result) {
                    var isOneFieldInvalid = result.find(function(elem) {
                        return !elem;
                    });
                    
                    var errorFields = document.querySelectorAll('#multi-form fieldset.active .validation-field.mf-error [data-validators], .opened .validation-field.mf-error [data-validators]');

                    if (errorFields.length > 0) {
                        errorFields[0].focus();
                    }
                    
                    isFormValidResult.resolve(isOneFieldInvalid === undefined ? true : isOneFieldInvalid);
                });
                
                return isFormValidResult.promise;
            }
        },

        setValidationClass = function(selector, isValid) {
            var field = getParents(document.querySelector(selector), '.validation-field');
            
            if (isValid) {
                removeClass(field, 'mf-error');
                addClass(field, 'mf-valid');
            } else {
                removeClass(field, 'mf-valid');
                addClass(field, 'mf-error', 'setVal');
            }
        },
        
        addClass = function(elem, className, t) {
            if (elem.className.indexOf(className) === -1) {
                elem.className += ' ' + className;
            }
        },
        
        removeClass = function(elem, className) {
            var classes = elem.className.replace(' ' + className, '');
            elem.className = classes;
        },
        
        getParents = function(el, findingElem) {
            var parents = [],
                p = el.parentNode;
        
            if (findingElem) {
                var findingElements = [].slice.call(document.querySelectorAll(findingElem));
                
                var isParentOfChild = findingElements.some(function(elem) {
                    return p === elem;
                });
                
                if (isParentOfChild) {
                    return p;
                }
                
                while (p !== findingElem) {
                    var o = p;
                    p = o.parentNode;
                }
                
                return p;
            } else {
                while (p !== document) {
                    var o = p;

                    parents.push(o);
                    p = o.parentNode;
                }

                return parents;
            }
        };

    [].slice.call(document.querySelectorAll('.validation-field [data-validators]')).forEach(function(validationField) {
        validationField.addEventListener('focus', function () {
            addClass(getParents(this, '.validation-field'), 'focused');
        }, true);
    });

    [].slice.call(document.querySelectorAll('.validation-field [data-validators]')).forEach(function(validationField) {
        validationField.addEventListener('blur', function () {
            removeClass(getParents(this, '.validation-field'), 'focused');

            if (this.getAttribute('type') === 'number' && typeof this.value === 'string') {
                this.value = (this.value.replace(/[^0-9]+/g, ''));
            }

            validateField(this);
        }, true);
    });

    [].slice.call(document.querySelectorAll('.validation-field [data-validators]')).forEach(function(validationField) {
        validationField.addEventListener('keyup', function () {
            validateField(this);
        }, true);
    });
    
    window.checkForm = checkForm;
    window.validation = validateField;
    window.setValidationClass = setValidationClass;
    window.removeClass = removeClass;
    window.addClass = addClass;
}());