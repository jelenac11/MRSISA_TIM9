Vue.component("dodavanje-klinike", {
	data : function() {
		return {
			novaKlinika : {
				naziv : "",
				lokacija : "",
				ocena : 0,
			},
	    	submitovano : false,
	    	uspesnoDodavanje : true,
	    	token : "",
	    }
	},
	template: `
	<div class="container d-flex justify-content-center">
		<div class="card mt-5" style="width: 47rem;">
			<h4 class="card-header">Nova klinika</h4>
			<div class="card-body">
				<form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="dodajKliniku" id="forma-dodaj-kliniku">
				  	<div class="form-row">
				    	<div class="col">
				    		<label for="naz" class="mt-1">Naziv</label>
							<input type="text" v-model="novaKlinika.naziv" class="form-control" id="naz" placeholder="Naziv" required>
				    		<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli naziv.</div>
				    	</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
				    		<label for="lok" class="mt-1">Lokacija</label>
							<input type="text" v-model="novaKlinika.lokacija" class="form-control" id="lok" placeholder="Lokacija" required>
				    		<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli lokaciju.</div>
				    	</div>
				  	</div>
				  	<button class="btn btn-lg btn-primary btn-block mt-4" type="submit">
				  		Dodaj
				  	</button>
				</form>
				<router-link :to="{ name: 'klinikeAdmin', params: { korisnikToken: this.token } }" class="btn btn-secondary">Nazad</router-link>
			</div>
		</div>
	</div>
	`
	,
	methods : {
		dodajKliniku : function () {
			this.submitovano = true;
			if (document.getElementById('forma-dodaj-kliniku').checkValidity() === true) {
				axios
				.post('klinike', this.novaKlinika, { headers: { Authorization: 'Bearer ' + this.token }} )
				.then(response => {
					this.uspesnoDodavanje = response.data;
					
					if (this.uspesnoDodavanje) {
						this.$router.replace({ name: 'klinikeAdmin', params: { korisnikToken: this.token } });
					}
				})
				.catch(error => {
					console.log(error);
					this.uspesnoDodavanje = false;
				});
			} else {
				this.uspesnoDodavanje = true;
			}
		},
	},
	mounted() {
		this.token = this.$route.params.korisnikToken;
	}
});