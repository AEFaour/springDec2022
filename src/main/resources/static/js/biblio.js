var biblioApp = angular.module('biblioApp', []);

biblioApp.controller ('livreController', [ '$http', '$scope', function($http, $scope) {
	$scope.livres = [];
	$scope.adherents = [];
	$scope.adherent = null;
	$scope.menu = 'accueil';
	$scope.livre = null;
	
	$scope.changeMenu = function(itemMenu) {
		$scope.menu = itemMenu;
	};
	
	$scope.loadAll = function() {
		$scope.livre = null;
		$http.get('rest/livres').then(function(value) {
			$scope.livres=value.data;
			console.log(value.data);
		}, function(reason) {
			//en cas de réponse en erreur reason contient l'erreur ....
			console.log(reason);
			
		});
	};
	$scope.load = function(id) {
	if(id != 0) {
		$http.get('rest/livres/'+id).then(function(value) {
			$scope.livre=value.data;
			console.log(value.data);
		}, function(reason) {
			//en cas de réponse en erreur reason contient l'erreur ....
			console.log(reason);
			
		});
		}
		else {
			$scope.livre={"id": 0,"titre": "","parution": 0,"auteur": ""};
		}
	};
	$scope.remove = function(id) {
		$http.delete('rest/livres/'+ id).then(function(value) {
			$scope.livre = null;
			$scope.loadAll();
		}, function(reason) {
			//en cas de réponse en erreur reason contient l'erreur ....
			console.log(reason);
			
		});
	};
	$scope.update = function(livre) {
		$http.put('rest/livres/'+ livre.id, livre).then(function(value) {
			$scope.livre = null;
			$scope.loadAll();
		}, function(reason) {
			//en cas de réponse en erreur reason contient l'erreur ....
			console.log(reason);
			
		});
	};
	
	$scope.create = function(livre) {
		$http.post('rest/livres', livre).then(function(value) {
			$scope.livre = null;
			$scope.loadAll();
		}, function(reason) {
			//en cas de réponse en erreur reason contient l'erreur ....
			console.log(reason);
			
		});
	};
	
	//
	// pour les adhrent
	$scope.loadAllAdherent = function() {
		$scope.adherent = null;
		$http.get('rest/adherents').then(function(value) {
			$scope.adherents=value.data;
			console.log(value.data);
		}, function(reason) {
			//en cas de réponse en erreur reason contient l'erreur ....
			console.log(reason);
			
		});
	};
	$scope.loadAdherent = function(id) {
	if(id != 0) {
		$http.get('rest/adherents/'+id).then(function(value) {
			$scope.adherent=value.data;
			console.log(value.data);
		}, function(reason) {
			//en cas de réponse en erreur reason contient l'erreur ....
			console.log(reason);
			
		});
		}
		else {
			$scope.adherent={"id" : 0, "nom" : "","prenom" : "", "tel" : "", "email" : ""};
		}
	};
	$scope.removeAdherent = function(id) {
		$http.delete('rest/adherents/'+ id).then(function(value) {
			$scope.adherent = null;
			$scope.loadAllAdherent();
		}, function(reason) {
			//en cas de réponse en erreur reason contient l'erreur ....
			console.log(reason);
			
		});
	};
	$scope.updateAdherent = function(adherent) {
		$http.put('rest/adherents/'+ adherent.id, adherent).then(function(value) {
			$scope.adherent = null;
			$scope.loadAllAdherent();
		}, function(reason) {
			//en cas de réponse en erreur reason contient l'erreur ....
			console.log(reason);
			
		});
	};
	
	$scope.createAdherent = function(adherent) {
		$http.post('rest/adherents', adherent).then(function(value) {
			$scope.adherent = null;
			$scope.loadAllAdherent();
		}, function(reason) {
			//en cas de réponse en erreur reason contient l'erreur ....
			console.log(reason);
			
		});
	};
	//
	//
	
	
	
	$scope.reset = function() {
		$scope.livres = [];
	};
	$scope.back = function() {
		$scope.livre = null;
	};
} ]);
