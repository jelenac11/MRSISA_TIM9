Vue.component("izmena-profila", {
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
	    	},
	    	izmenjeniKorisnik : {},
	    	submitovano : false,
	    	uspesnaIzmena : true,
	    	novaLozinka : "",
	    	potvrdaLozinke : "",
	    	poklapajuSeLozinke : true,
	    }
	},
	template: `
		<div class="container d-flex justify-content-center">
			<div class="card mt-5" style="width: 70rem;">
				<h4 class="card-header">Izmena podataka o profilu</h4>
				<div class="card-body">
					<form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="izmenaPod" id="forma-izmena">
						<div class="form-row">
							<div class="col">
					    	 	<label for="email">Email adresa</label>
								<input type="email" v-model="korisnik.email" class="form-control" id="email" placeholder="Email adresa" disabled required>
							</div>
					    	<div class="col">
					    	 	<label for="lozinka1">Lozinka</label>
								<input type="password" minlength="8" maxlength="20" v-model="novaLozinka" class="form-control" id="lozinka1" placeholder="Lozinka">
								<small id="passwordHelpBlock" class="form-text text-muted">
								  	Lozinka mora imati 8-20 karaktera.
								</small>
								<div class="invalid-feedback" id="izmenaInvalid">Neodgovarajuća dužina lozinke.</div>
							</div>
					    	<div class="col">
					    		<label for="lozinka2">Potvrdite lozinku</label>
								<input type="password" v-model="potvrdaLozinke" class="form-control" v-bind:class="{ nePoklapajuSe : !poklapajuSeLozinke }" id="lozinka2" v-bind:disabled="novaLozinka == ''" placeholder="Lozinka">
								<div class="invalid-feedback" v-bind:class="{ 'd-block' : !poklapajuSeLozinke }" id="dodavanjeInvalid">Lozinke se ne poklapaju!</div>
					    	</div>
					  	</div>
					  	<div class="form-row">
					    	<div class="col">
					    	 	<label for="ime" class="mt-1">Ime</label>
								<input type="text" v-model="izmenjeniKorisnik.ime" class="form-control" id="ime" placeholder="Ime" required>
								<div class="invalid-feedback" id="izmenaInvalid">Niste uneli ime.</div>
							</div>
					    	<div class="col">
					    		<label for="prezime" class="mt-1">Prezime</label>
								<input type="text" v-model="izmenjeniKorisnik.prezime" class="form-control" id="prezime" placeholder="Prezime" required>
					    		<div class="invalid-feedback" id="izmenaInvalid">Niste uneli prezime.</div>
					    	</div>
					    </div>
					    <div class="form-row">
					    	<div class="col">
					    		<label for="adresa" class="mt-1">Adresa</label>
								<input type="text" v-model="izmenjeniKorisnik.adresa" class="form-control" id="adresa" placeholder="Adresa" required>
					    		<div class="invalid-feedback" id="izmenaInvalid">Niste uneli adresu.</div>
					    	</div>
					    	<div class="col">
					    		<label for="grad" class="mt-1">Grad</label>
								<input type="text" v-model="izmenjeniKorisnik.grad" class="form-control" id="grad" placeholder="Grad" required>
					    		<div class="invalid-feedback" id="izmenaInvalid">Niste uneli grad.</div>
					    	</div>
					    	<div class="col">
					    		<label for="drzava" class="mt-1">Drzava</label>
								<input type="text" v-model="izmenjeniKorisnik.drzava" class="form-control" id="drzava" placeholder="Drzava" required>
					    		<div class="invalid-feedback" id="izmenaInvalid">Niste uneli drzavu.</div>
					    	</div>
					  	</div>
					  	<button class="btn btn-lg btn-primary btn-block mt-4" type="submit" v-bind:disabled="izmenjeniKorisnik.ime == korisnik.ime && izmenjeniKorisnik.prezime == korisnik.prezime && izmenjeniKorisnik.adresa == korisnik.adresa && izmenjeniKorisnik.grad == korisnik.grad && izmenjeniKorisnik.drzava == korisnik.drzava && novaLozinka == ''">
					  		Sačuvaj izmene
					  	</button>
					</form>
					<router-link :to="{ path: '/' }" class="btn btn-secondary">Nazad</router-link>
				</div>
			</div>
		</div>	
		`
			,
			methods : {
				izmenaPod : function () {
					this.proveriLozinke();
					this.submitovano = true;
					if (document.getElementById('forma-izmena').checkValidity() === true && this.poklapajuSeLozinke) {
						axios
						.put('/korisnici', this.izmenjeniKorisnik)
						.then(response => {
							this.uspesnaIzmena = true;
							toast("Uspešno izmenjeni podaci");
						})
						.catch(function (error) { console.log(error);this.uspesnaIzmena = false; });
					} else {
						this.uspesnaIzmena = true;
					}
				},
				proveriLozinke : function () {
					if (this.novaLozinka != "") {
						this.izmenjeniKorisnik.lozinka = this.novaLozinka
						if (this.novaLozinka != this.potvrdaLozinke) {
							this.poklapajuSeLozinke = false;
						} else {
							this.poklapajuSeLozinke = true;
						}
					} else {
						this.poklapajuSeLozinke = true;
						this.izmenjeniKorisnik.lozinka = this.korisnik.lozinka;
					}
				},
			},
			mounted () {
				axios
		        .get('/korisnici/dobaviUlogovanog')
		        .then(response => {
		        	this.korisnik = response.data; 
		        	this.izmenjeniKorisnik = JSON.parse(JSON.stringify(this.korisnik));
		        })
		        .catch(function (error) { console.log(error); });
			}
});