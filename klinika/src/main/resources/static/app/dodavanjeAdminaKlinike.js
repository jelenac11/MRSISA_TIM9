Vue.component("dodavanje-admina-klinike", {
	data : function() {
		return {
			noviAdmin : {
				email : "",
				ime : "",
				prezime : "",
				lozinka : "",
				adresa : "",
				grad : "",
				drzava : "",
				brojTelefona : "",
				klinika : "",
			},
			potvrdaLozinke : "",
			poklapajuSeLozinke : true,
	    	submitovano : false,
	    	uspesnoDodavanje : true,
	    	zauzetEmail : false,
	    	klinike : [],
	    	token : "",
	    }
	},
	template: `
	<div class="container d-flex justify-content-center">
		<div class="card mt-5" style="width: 47rem;">
			<h4 class="card-header">Novi administrator klinike</h4>
			<div class="card-body">
				<form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="dodajAdmina" id="forma-dodaj-admina">
				  	<div class="form-row mb-3">
				  		<div class="col">
				    	 	<label for="email">Email adresa</label>
							<input type="email" v-model="noviAdmin.email" class="form-control" id="email" placeholder="Email" required>
							<div class="invalid-feedback" id="dodavanjeInvalid">Neispravan email.</div>
						</div>
					</div>
					<div class="form-row">
				    	<div class="col">
				    	 	<label for="lozinka1">Lozinka</label>
							<input type="password" minlength="8" maxlength="20" v-model="noviAdmin.lozinka" class="form-control" id="lozinka1" placeholder="Lozinka" required>
							<small id="passwordHelpBlock" class="form-text text-muted">
							  	Lozinka mora imati 8-20 karaktera.
							</small>
							<div class="invalid-feedback" id="dodavanjeInvalid">Neodgovarajuća dužina lozinke.</div>
						</div>
				    	<div class="col">
				    		<label for="lozinka2">Potvrdite lozinku</label>
							<input type="password" v-model="potvrdaLozinke" class="form-control" v-bind:class="{ nePoklapajuSe : !poklapajuSeLozinke }" id="lozinka2" placeholder="Lozinka">
							<div class="invalid-feedback" v-bind:class="{ 'd-block' : !poklapajuSeLozinke }" id="dodavanjeInvalid">Lozinke se ne poklapaju!</div>
				    	</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
				    	 	<label for="ime" class="mt-1">Ime</label>
							<input type="text" v-model="noviAdmin.ime" class="form-control" id="ime" placeholder="Ime" required>
							<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli ime.</div>
						</div>
				    	<div class="col">
				    		<label for="prezime" class="mt-1">Prezime</label>
							<input type="text" v-model="noviAdmin.prezime" class="form-control" id="prezime" placeholder="Prezime" required>
				    		<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli prezime.</div>
				    	</div>
				    	<div class="col">
				    		<label for="adresa" class="mt-1">Adresa</label>
							<input type="text" v-model="noviAdmin.adresa" class="form-control" id="adresa" placeholder="Adresa" required>
				    		<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli adresu.</div>
				    	</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
				    		<label for="grad" class="mt-1">Grad</label>
							<input type="text" v-model="noviAdmin.grad" class="form-control" id="grad" placeholder="Grad" required>
				    		<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli grad.</div>
				    	</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
				    		<label for="drzava" class="mt-1">Drzava</label>
							<input type="text" v-model="noviAdmin.drzava" class="form-control" id="drzava" placeholder="Drzava" required>
				    		<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli državu.</div>
				    	</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
				    		<label for="tel" class="mt-1">Broj telefona</label>
							<input type="text" v-model="noviAdmin.brojTelefona" class="form-control" id="tel" placeholder="Broj telefona" required>
				    		<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli brojTelefona.</div>
				    	</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
				    		<label for="klin" class="mt-1">Klinika</label>
							<select class="custom-select mt-0" v-model="noviAdmin.klinika" id="klin" required>
						    	<option v-for="k in klinike" :value="k.naziv">
									{{ k.naziv }}
						    	</option>
						  	</select>
						  	<div class="invalid-feedback" id="dodavanjeInvalid">Niste izabrali kliniku.</div>
						</div>
				  	</div>
				  	<div v-if=zauzetEmail class="alert alert-danger" role="alert">
						<p class="mb-0"><b>Greška!</b> Već postoji korisnik sa unetim Email-om. Pokušajte ponovo.</p>
					</div>
				  	<button class="btn btn-lg btn-primary btn-block mt-4" type="submit">
				  		Dodaj
				  	</button>
				</form>
				<router-link :to="{ name: 'adminiKlinike' }" class="btn btn-secondary">Nazad</router-link>
			</div>
		</div>
	</div>
	`
	,
	methods : {
		dodajAdmina : function () {
			this.proveriLozinke();
			this.submitovano = true;
			if (document.getElementById('forma-dodaj-admina').checkValidity() === true && this.poklapajuSeLozinke) {
				axios
				.get('/korisnici/proveriEmail/' + this.noviAdmin.email, { headers: { Authorization: 'Bearer ' + this.token }} )
				.then(response => {
					axios
					.post('adminiKlinike', this.noviAdmin, { headers: { Authorization: 'Bearer ' + this.token }} )
					.then(response => {
						this.uspesnoDodavanje = response.data;
						
						if (this.uspesnoDodavanje) {
							this.$router.replace({ name: 'adminiKlinike' });
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
					this.zauzetEmail = true;
				});
			} else {
				this.uspesnoDodavanje = true;
				this.zauzetEmail = false;
			}
		},
		proveriLozinke : function () {
			if (this.noviAdmin.lozinka != this.potvrdaLozinke) {
				this.poklapajuSeLozinke = false;
			} else {
				this.poklapajuSeLozinke = true;
			}
		}
	},
	mounted() {
		this.token = localStorage.getItem("token");
		axios
        .get('klinike/ucitajSve', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => (this.klinike = response.data))
        .catch(function (error) { console.log(error); });
	}
});