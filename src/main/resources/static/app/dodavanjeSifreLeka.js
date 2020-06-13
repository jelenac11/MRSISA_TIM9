Vue.component("dodavanje-sifre-leka", {
	data : function() {
		return {
			novaStavka : {
				sifra : "",
				naziv : "",
				tipSifre : "LEKOVI",
			},
	    	submitovano : false,
	    	uspesnoDodavanje : true,
	    	zauzetaSifra : false,
	    	token : "",
	    }
	},
	template: `
	<div class="container d-flex justify-content-center">
		<div class="card mt-5" style="width: 47rem;">
			<h4 class="card-header">Nova šifra leka</h4>
			<div class="card-body">
				<form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="dodajStavku" id="forma-dodaj-stavku">
				  	<div class="form-row mb-3">
				  		<div class="col">
				    	 	<label for="sif">Šifra</label>
							<input type="text" v-model="novaStavka.sifra" class="form-control" id="sif" placeholder="Šifra" required></input>
							<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli šifru.</div>
						</div>
					</div>
				  	<div class="form-row">
				    	<div class="col">
				    	 	<label for="naz">Naziv</label>
							<input type="text" v-model="novaStavka.naziv" class="form-control" id="naz" placeholder="Naziv" required></input>
							<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli naziv leka.</div>
						</div>
				  	</div>
				  	<div v-if=zauzetaSifra class="alert alert-danger" role="alert">
						<p class="mb-0"><b>Greška!</b> Već postoji ta šifra. Pokušajte ponovo.</p>
					</div>
				  	<button style="color:white" class="btn btn-lg btn-primary btn-block mt-4" type="submit">
				  		Dodaj
				  	</button>
				</form>
				<router-link :to="{ name: 'sifrarnikLekova' }" class="btn btn-secondary">Nazad</router-link>
			</div>
		</div>
	</div>
	`
	,
	methods : {
		dodajStavku : function () {
			this.submitovano = true;
			if (document.getElementById('forma-dodaj-stavku').checkValidity() === true) {
				axios
				.get('/stavkeSifrarnika/proveriSifru/' + this.novaStavka.sifra, { headers: { Authorization: 'Bearer ' + this.token }} )
				.then(response => {
					axios
					.post('/stavkeSifrarnika', this.novaStavka, { headers: { Authorization: 'Bearer ' + this.token }} )
					.then(response => {
						this.uspesnoDodavanje = response.data;
						
						if (this.uspesnoDodavanje) {
							this.$router.replace({ name: 'sifrarnikLekova' });
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
					this.zauzetaSifra = true;
				});
			} else {
				this.uspesnoDodavanje = true;
			}
		}
	},
	mounted() {
		this.token = localStorage.getItem("token");
	}
});