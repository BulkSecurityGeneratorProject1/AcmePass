(function () {
    'use strict';

    angular
        .module('acmeApp')
        .controller('ACMEPassPwdGenController', ACMEPassPwdGenController);

    ACMEPassPwdGenController.$inject = ['$timeout', '$scope', '$uibModalInstance'];

    function ACMEPassPwdGenController($timeout, $scope, $uibModalInstance) {
        var vm = this;

        vm.clear = clear;
        vm.generate = generate;
        vm.save = save;

        vm.genOptions = {
            length: 8,
            lower: true,
            upper: true,
            digits: true,
            special: true,
            repetition: false
        };

        vm.chars = {
            lower: "qwertyuiopasdfghjklzxcvbnm",
            upper: "QWERTYUIOPASDFGHJKLZXCVBNM",
            digits: "0123456789",
            special: "!@#$%-_"
        };

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function generate() {
            var chars = "";
            vm.password = "";
            
            // Variable that tracks whether at least one generation option is checked.
            var optionChecked = false;

            if (vm.genOptions.lower) {
            	optionChecked = true;
                chars += vm.chars.lower;
            }

            if (vm.genOptions.upper) {
            	optionChecked = true;
                chars += vm.chars.upper;
            }

            if (vm.genOptions.digits) {
            	optionChecked = true;
                chars += vm.chars.digits;
            }

            if (vm.genOptions.special) {
            	optionChecked = true;
                chars += vm.chars.special;
            }

            while (vm.password.length < vm.genOptions.length && optionChecked) {
                var position = Math.floor(Math.random() * chars.length);

                if (vm.genOptions.repetition) {
                	if (vm.genOptions.length > chars.length) {
                		vm.password = "";
                		break;
                	}
                    if (vm.password.indexOf(chars[position]) === -1) {
                        vm.password += chars[position];
                    }
                } else {
                    vm.password += chars[position];
                }
            }
        }

        function save() {
            $scope.$emit('acmeApp:ACMEPassPwdGen', vm.password);
            $uibModalInstance.close(vm.password);
        }
    }
})();
