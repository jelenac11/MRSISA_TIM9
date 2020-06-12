Vue.component("registracija", {
	data : function() {
		 return {
	    	korisnik : {
	    		email : "",
	    		lozinka : "",
	    		ime : "",
	    		prezime : "",
	    		adresa : "",
	    		grad : "",
	    		drzava : "",
	    		brojTelefona : "",
	    		jbo : "",
	    		aktiviran : false
	    	},
	    	submitovano : false,
	    	novaLozinka : "",
	    	potvrdaLozinke : "",
	    	poklapajuSeLozinke : true,
	    	postojiEmail : false,
	    	postojiJbo : false,
	    	porukaUspeha : false,
	    }
	},
	template: `
	<div class="container d-flex justify-content-center">
		<div class="card mt-5" style="width: 70rem;">
			<h4 class="card-header">Forma za registraciju</h4>
			<div class="card-body">
				<form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="reg" id="forma-reg">
					<div class="form-row">
						<div class="col">
				    	 	<label for="email">Email adresa</label>
							<input type="email" v-model="korisnik.email" class="form-control" id="email" placeholder="Email adresa" required>
							<div class="invalid-feedback">Niste uneli email.</div>
						</div>
				    	<div class="col">
				    	 	<label for="lozinka1">Lozinka</label>
							<input type="password" minlength="8" maxlength="20" v-model="novaLozinka" class="form-control" id="lozinka1" placeholder="Lozinka" required>
							<small id="passwordHelpBlock" class="form-text text-muted">
							  	Lozinka mora imati 8-20 karaktera.
							</small>
							<div class="invalid-feedback">Neodgovarajuća dužina lozinke.</div>
						</div>
				    	<div class="col">
				    		<label for="lozinka2">Potvrdite lozinku</label>
							<input type="password" v-model="potvrdaLozinke" class="form-control" v-bind:class="{ nePoklapajuSe : !poklapajuSeLozinke }" id="lozinka2" v-bind:disabled="novaLozinka == ''" placeholder="Lozinka" required>
							<div class="invalid-feedback" v-bind:class="{ 'd-block' : !poklapajuSeLozinke }">Lozinke se ne poklapaju!</div>
				    	</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
				    	 	<label for="ime" class="mt-1">Ime</label>
							<input type="text" v-model="korisnik.ime" class="form-control" id="ime" placeholder="Ime" required>
							<div class="invalid-feedback">Niste uneli ime.</div>
						</div>
				    	<div class="col">
				    		<label for="prezime" class="mt-1">Prezime</label>
							<input type="text" v-model="korisnik.prezime" class="form-control" id="prezime" placeholder="Prezime" required>
				    		<div class="invalid-feedback">Niste uneli prezime.</div>
				    	</div>
				    </div>
				    <div class="form-row">
				    	<div class="col">
				    		<label for="adresa" class="mt-1">Adresa</label>
							<input type="text" v-model="korisnik.adresa" class="form-control" id="adresa" placeholder="Adresa" required>
				    		<div class="invalid-feedback">Niste uneli adresu.</div>
				    	</div>
				    	<div class="col">
				    		<label for="grad" class="mt-1">Grad</label>
							<input type="text" v-model="korisnik.grad" class="form-control" id="grad" placeholder="Grad" required>
				    		<div class="invalid-feedback">Niste uneli grad.</div>
				    	</div>
				    	<div class="col">
				    		<label for="drzava" class="mt-1">Drzava</label>
							<input type="text" v-model="korisnik.drzava" class="form-control" id="drzava" placeholder="Drzava" required>
				    		<div class="invalid-feedback">Niste uneli drzavu.</div>
				    	</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
				    		<label for="brTelefona" class="mt-1">Broj telefona</label>
							<input type="text" v-model="korisnik.brojTelefona" class="form-control" id="brTelefona" placeholder="Broj telefona" required>
				    		<div class="invalid-feedback">Niste uneli broj telefona.</div>
				    	</div>
				    	<div class="col">
				    		<label for="jbo" class="mt-1">JBO</label>
							<input type="text" v-model="korisnik.jbo" class="form-control" id="jbo" placeholder="JBO" required>
				    		<div class="invalid-feedback">Niste uneli jbo.</div>
				    	</div>
				  	</div>
				  	<div v-if=postojiEmail class="alert alert-danger" role="alert">
						<p class="mb-0"><b>Greška!</b> Već postoji korisnik sa unetim Email-om. Pokušajte ponovo.</p>
					</div>
					<div v-if=postojiJbo class="alert alert-danger" role="alert">
						<p class="mb-0"><b>Greška!</b> JBO koji ste uneli već postoji. Pokušajte ponovo.</p>
					</div>
					<div v-if=porukaUspeha class="alert alert-success mt-4" role="alert">
						<p class="mb-0"><b>Zahtev za registracijom je poslat! Kada administrator odobri zahtev, dobićete aktivacioni link na vašu Email adresu.</b></p>
					</div>
				  	<button style="color:white;" class="btn btn-lg btn-primary btn-block mt-4" type="submit">
				  		Registruj se
				  	</button>
				</form>
				<router-link :to="{ name: 'login' }" class="btn btn-secondary">Nazad</router-link>
			</div>
		</div>
	</div>	
	`
	,
	methods : {
		reg : function () {
			this.proveriLozinke();
			this.submitovano = true;
			if (document.getElementById('forma-reg').checkValidity() === true && this.poklapajuSeLozinke) {
				axios
				.put('/auth/proveriEmail', this.korisnik)
				.then(response => {
					if (response.data) {
						this.postojiEmail = true;
						this.postojiJbo = false;
						this.porukaUspeha = false;
					} else {
						axios
						.put('/auth/proveriJbo', this.korisnik)
						.then(response => {
							if (response.data) {
								this.postojiJbo = true;
								this.postojiEmail = false;
								this.porukaUspeha = false;
							} else {
								axios
								.post('/auth/dodajRegZahtev', this.korisnik)
								.then(response => {
									this.porukaUspeha = true;
									this.postojiEmail = false;
									this.postojiJbo = false;
									this.submitovano = false;
									this.isprazniPolja(); 
								})
								.catch(function (error) { console.log(error); });
							}
						})
						.catch(function (error) { console.log(error); });
					}
				})
				.catch(function (error) { console.log(error); });
			} else {
				this.postojiEmail = false;
				this.postojiJbo = false;
				this.porukaUspeha = false;
			}
		},
		proveriLozinke : function () {
			if (this.novaLozinka != "") {
				if (this.novaLozinka != this.potvrdaLozinke) {
					this.poklapajuSeLozinke = false;
				} else {
					this.poklapajuSeLozinke = true;
					this.korisnik.lozinka = this.novaLozinka;
				}
			} else {
				this.poklapajuSeLozinke = true;
				this.korisnik.lozinka = this.novaLozinka;
			}
		},
		isprazniPolja : function () {
			this.korisnik = {
	    		email : "",
	    		lozinka : "",
	    		ime : "",
	    		prezime : "",
	    		adresa : "",
	    		grad : "",
	    		drzava : "",
	    		brojTelefona : "",
	    		jbo : "",
	    		aktiviran : false
	    	};
			this.novaLozinka = "";
	    	this.potvrdaLozinke = "";
		},
	}
});