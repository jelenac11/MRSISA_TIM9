Vue.component("dodavanje-sale", {
	data : function() {
		return {
			novaSala : {
				broj: 0,
				naziv: ""
			},
	    	submitovano : false,
	    	uspesnoDodavanje : true,
	    	zauzetBroj : false,
	    	token : "",
	    }
	},
	template: `
	<div class="container d-flex justify-content-center">
		<div class="card mt-5" style="width: 47rem;">
			<h4 class="card-header">Nova sala</h4>
			<div class="card-body">
				<form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="dodajSalu" id="forma-dodaj-salu">
				  	<div class="form-row mb-3">
				  		<div class="col">
				    	 	<label for="brsale">Broj sale</label>
							<input type="number" v-model="novaSala.broj" min="1" class="form-control" id="brsale" placeholder="Broj sale" required>
							<div class="invalid-feedback" id="dodavanjeInvalid">Unesite ispravan broj sale.</div>
						</div>
					</div>
				  	<div class="form-row">
				    	<div class="col">
				    	 	<label for="inf">Naziv</label>
							<input type="text" v-model="novaSala.naziv" class="form-control" id="naz" placeholder="Naziv" required></input>
							<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli naziv sale.</div>
						</div>
				  	</div>
				  	<div v-if=zauzetBroj class="alert alert-danger" role="alert">
						<p class="mb-0"><b>Greška!</b> Već postoji sala sa unetim brojem. Pokušajte ponovo.</p>
					</div>
				  	<button class="btn btn-lg btn-primary btn-block mt-4" type="submit">
				  		Dodaj
				  	</button>
				</form>
				<router-link :to="{ name: 'sale' }" class="btn btn-secondary">Nazad</router-link>
			</div>
		</div>
	</div>
	`
	,
	methods : {
		dodajSalu : function () {
			this.submitovano = true;
			if (document.getElementById('forma-dodaj-salu').checkValidity() === true) {
				axios
				.get('/sale/proveriBroj/' + this.novaSala.broj, { headers: { Authorization: 'Bearer ' + this.token }} )
				.then(response => {
					axios
					.post('/sale', this.novaSala, { headers: { Authorization: 'Bearer ' + this.token }} )
					.then(response => {
						this.uspesnoDodavanje = response.data;
						
						if (this.uspesnoDodavanje) {
							this.$router.replace({ name: 'sale' });
						}
					})
					.catch(error => {
						console.log(error);
						this.uspesnoDodavanje = false;
					});
				})
				.catch(error => {
					console.log(error);
					this.uspesnoDodavanje = false;
					this.zauzetBroj = true;
				});
			} else {
				this.uspesnoDodavanje = true;
				this.zauzetBroj = false;
			}
		}
	},
	mounted() {
		this.token = localStorage.getItem("token");
	}
});