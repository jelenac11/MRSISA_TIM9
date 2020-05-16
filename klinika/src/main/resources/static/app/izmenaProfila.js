Vue.component("izmena-profila", {
	data : function() {
		 return {
	    	korisnik : {},
	    	izmenjeniKorisnik : {},
	    	submitovano : false,
	    	submitovanoLoz : false,
	    	uspesnaIzmena : true,
	    	uspesnaIzmenaLoz : true,
	    	promenjenaLozinka : false,
	    	novaLozinka : "",
	    	potvrdaLozinke : "",
	    	poklapajuSeLozinke : true,
	    	uloga : "",
	    	token : "",
	    }
	},
	template: `
	<div class="container d-flex justify-content-center">
		<div class="card mt-5" style="width: 220rem;">
			<h4 class="card-header">Izmena podataka o profilu</h4>
			<div class="card-body">
				<form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="izmenaPod" id="forma-izmena">
					<div class="form-row">
						<div class="col">
				    	 	<label for="email">Email adresa</label>
							<input type="email" v-model="izmenjeniKorisnik.email" class="form-control" id="email" placeholder="Email adresa" disabled required>
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
				    		<label for="drzava" class="mt-1">Država</label>
							<input type="text" v-model="izmenjeniKorisnik.drzava" class="form-control" id="drzava" placeholder="Država" required>
				    		<div class="invalid-feedback" id="izmenaInvalid">Niste uneli državu.</div>
				    	</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
				    		<label for="brojTelefona" class="mt-1">Broj telefona</label>
							<input type="text" v-model="izmenjeniKorisnik.brojTelefona" class="form-control" id="brojTelefona" placeholder="Broj telefona" required>
				    		<div class="invalid-feedback" id="izmenaInvalid">Niste uneli broj telefona.</div>
				    	</div>
				  	</div>
				  	<div v-if="this.uloga == 'ROLE_PACIJENT'" class="form-row">
				    	<div class="col">
				    		<label for="adresa" class="mt-1">Jedinstveni broj osiguranika - JBO</label>
							<input type="text" v-model="izmenjeniKorisnik.jbo" class="form-control" id="jbo" placeholder="JBO" disabled required>
				    	</div>
				  	</div>
				  	<div v-if="this.uloga == 'ROLE_LEKAR' || this.uloga == 'ROLE_MED_SESTRA'" class="form-row">
				    	<div class="col">
				    		<label for="poc" class="mt-1">Početak radnog vremena</label>
							<input type="text" :value="sati(izmenjeniKorisnik.pocetakRadnogVremena)" class="form-control" id="poc" placeholder="Početak radnog vremena" disabled required>
				    	</div>
				    	<div class="col">
				    		<label for="kraj" class="mt-1">Kraj radnog vremena</label>
							<input type="text" :value="sati(izmenjeniKorisnik.krajRadnogVremena)" class="form-control" id="kraj" placeholder="Kraj radnog vremena" disabled required>
				    	</div>
				  	</div>
				  	<div v-if="this.uloga == 'ROLE_ADMIN_KLINIKE'" class="form-row">
				    	<div class="col">
				    		<label for="klinika" class="mt-1">Klinika</label>
							<input type="text" v-model="izmenjeniKorisnik.klinika" class="form-control" id="klinika" placeholder="Klinika" disabled required>
				    	</div>
				  	</div>
				  	<button class="btn btn-lg btn-primary btn-block mt-4" type="submit">
				  		Sačuvaj izmene
				  	</button>
				</form>
			</div>
			<h4 class="card-header">Promena lozinke</h4>
			<div class="card-body">
				<form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovanoLoz }" novalidate @submit.prevent="izmenaLoz" id="forma-loz">
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
			    	<button class="btn btn-lg btn-primary btn-block mt-4" type="submit" v-bind:disabled="novaLozinka == ''">
				  		Promeni lozinku
				  	</button>
				</form>
				<button class="btn btn-lg btn-secondary btn-block mt-4" v-on:click="nazad">Nazad</button>
			</div>
		</div>
	</div>
	`
	,
	methods : {
		sati : function (vreme) {
		    var minutes = Math.floor((vreme / (1000 * 60)) % 60);
		    var hours = Math.floor((vreme / (1000 * 60 * 60)) % 24);
	
			hours = (hours < 10) ? "0" + hours : hours;
			minutes = (minutes < 10) ? "0" + minutes : minutes;

			return (hours + ":" + minutes);
		},
		izmenaPod : function () {
			if (this.promenjenaLozinka) {
				this.$router.replace({ name: 'login' });
			}
			this.submitovano = true;
			if (document.getElementById('forma-izmena').checkValidity() === true) {
				axios
		        .get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
		        .then(response => {
		        	this.korisnik = response.data;
		        	axios
		    		.put('/korisnici/dobaviUlogu', this.korisnik, { headers: { Authorization: 'Bearer ' + this.token }} )
		            .then(response => {
		            	this.uloga = response.data;
		            	if (this.uloga == "ROLE_PACIJENT") {
		    				axios
		    				.put('/pacijenti', this.izmenjeniKorisnik, { headers: { Authorization: 'Bearer ' + this.token }} )
		    				.then(response => {
		    					this.uspesnaIzmena = true;
		    					toast("Uspešno izmenjeni podaci");
		    					this.korisnik = JSON.parse(JSON.stringify(this.izmenjeniKorisnik));
		    				})
		    				.catch(function (error) { console.log(error); this.uspesnaIzmena = false; });
		            	} else if (this.uloga == "ROLE_LEKAR") {
		    				axios
		    				.put('/lekari', this.izmenjeniKorisnik, { headers: { Authorization: 'Bearer ' + this.token }} )
		    				.then(response => {
		    					this.uspesnaIzmena = true;
		    					toast("Uspešno izmenjeni podaci");
		    					this.korisnik = JSON.parse(JSON.stringify(this.izmenjeniKorisnik));
		    				})
		    				.catch(function (error) { console.log(error); this.uspesnaIzmena = false; });
		            	} else if (this.uloga == "ROLE_MED_SESTRA") {
		    				axios
		    				.put('/sestre', this.izmenjeniKorisnik, { headers: { Authorization: 'Bearer ' + this.token }} )
		    				.then(response => {
		    					this.uspesnaIzmena = true;
		    					toast("Uspešno izmenjeni podaci");
		    					this.korisnik = JSON.parse(JSON.stringify(this.izmenjeniKorisnik));
		    				})
		    				.catch(function (error) { console.log(error); this.uspesnaIzmena = false; });
		            	} else if (this.uloga == "ROLE_ADMIN_KLINICKOG_CENTRA") {
		    				axios
		    				.put('/adminiCentra', this.izmenjeniKorisnik, { headers: { Authorization: 'Bearer ' + this.token }} )
		    				.then(response => {
		    					this.uspesnaIzmena = true;
		    					toast("Uspešno izmenjeni podaci");
		    					this.korisnik = JSON.parse(JSON.stringify(this.izmenjeniKorisnik));
		    				})
		    				.catch(function (error) { console.log(error); this.uspesnaIzmena = false; });
		            	} else if (this.uloga == "ROLE_ADMIN_KLINIKE") {
		    				axios
		    				.put('/adminiKlinike', this.izmenjeniKorisnik, { headers: { Authorization: 'Bearer ' + this.token }} )
		    				.then(response => {
		    					this.uspesnaIzmena = true;
		    					toast("Uspešno izmenjeni podaci");
		    					this.korisnik = JSON.parse(JSON.stringify(this.izmenjeniKorisnik));
		    				})
		    				.catch(function (error) { console.log(error); this.uspesnaIzmena = false; });
		            	}
		            })
		            .catch(function (error) { console.log(error); });
		        })
		        .catch(function (error) { console.log(error); });
			} else {
				this.uspesnaIzmena = true;
			}
		},
		izmenaLoz : function() {
			if (this.promenjenaLozinka) {
				this.$router.replace({ name: 'login' });
			}
			this.submitovanoLoz = true;
			this.proveriLozinke();
			if (document.getElementById('forma-loz').checkValidity() === true && this.poklapajuSeLozinke) {
				axios
				.put('korisnici/promeniLozinku', { lozinka: this.novaLozinka }, { headers: { Authorization: 'Bearer ' + this.token }} )
				.then(response => {
					this.promenjenaLozinka = true;
					this.uspesnaIzmenaLoz = true;
					toast("Uspešno izmenjena lozinka");
					this.korisnik.lozinka = JSON.parse(JSON.stringify(this.novaLozinka));
					this.izmenjeniKorisnik.lozinka = JSON.parse(JSON.stringify(this.novaLozinka));
					this.novaLozinka = "";
					this.potvrdaLozinke = "";
				})
				.catch(function (error) { console.log(error); this.uspesnaIzmena = false; });
			} else {
				this.uspesnaIzmenaLoz = true;
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
			}
		},
		nazad : function () {
			if (this.promenjenaLozinka) {
				this.$router.replace({ name: 'login' });
			} else {
				this.$router.go(-1);
			}
		},
	},
	mounted () {
		this.token = localStorage.getItem("token");
		axios
        .get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => {
        	this.korisnik = response.data; 
        	this.izmenjeniKorisnik = JSON.parse(JSON.stringify(this.korisnik));
        	
        	axios
    		.put('/korisnici/dobaviUlogu', this.korisnik, { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => { 
            	this.uloga = response.data;
            	if (this.uloga == "ROLE_PACIJENT") {
            		axios
            		.get('/pacijenti/' + this.korisnik.id, { headers: { Authorization: 'Bearer ' + this.token }} )
                    .then(response => { 
                    	this.korisnikUloga = response.data;
                    	this.izmenjeniKorisnik = JSON.parse(JSON.stringify(this.korisnikUloga));
                    })
                    .catch(function (error) { console.log(error); });
            	}
            	else if (this.uloga == "ROLE_LEKAR") {
            		axios
            		.get('/lekari/' + this.korisnik.id, { headers: { Authorization: 'Bearer ' + this.token }} )
                    .then(response => { 
                    	this.korisnikUloga = response.data;
                    	this.izmenjeniKorisnik = JSON.parse(JSON.stringify(this.korisnikUloga));
                    })
                    .catch(function (error) { console.log(error); });
            	}
            	else if (this.uloga == "ROLE_MED_SESTRA") {
            		axios
            		.get('/sestre/' + this.korisnik.id, { headers: { Authorization: 'Bearer ' + this.token }} )
                    .then(response => { 
                    	this.korisnikUloga = response.data;
                    	this.izmenjeniKorisnik = JSON.parse(JSON.stringify(this.korisnikUloga));
                    })
                    .catch(function (error) { console.log(error); });
            	}
            	else if (this.uloga == "ROLE_ADMIN_KLINIKE") {
            		axios
            		.get('/adminiKlinike/' + this.korisnik.id, { headers: { Authorization: 'Bearer ' + this.token }} )
                    .then(response => { 
                    	this.korisnikUloga = response.data;
                    	this.izmenjeniKorisnik = JSON.parse(JSON.stringify(this.korisnikUloga));
                    })
                    .catch(function (error) { console.log(error); });
            	}
            })
            .catch(function (error) { console.log(error); });
        	
        })
        .catch(function (error) { console.log(error); });
	}
});