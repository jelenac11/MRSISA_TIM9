Vue.component("dodavanje-medSestara", {
	data : function() {
		return {
			novaMedSestra : {
				email : "",
				ime : "",
				prezime : "",
				lozinka : "",
				adresa : "",
				grad : "",
				drzava : "",
				brojTelefona : "",
				pocetakRadnogVremena : 0,
				krajRadnogVremena : 0,
				klinika: null,
			},
			potvrdaLozinke : "",
			poklapajuSeLozinke : true,
	    	submitovano : false,
	    	pocetakRadnog : "",
			krajRadnog : "",
	    	uspesnoDodavanje : true,
	    	zauzetEmail : false,
	    	token : "",
	    }
	},
	template: `
	<div class="container d-flex justify-content-center">
		<div class="card mt-5" style="width: 47rem;">
			<h4 class="card-header">Nova medicinska sestra</h4>
			<div class="card-body">
				<form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="dodajMedSestru" id="forma-dodaj-medSestru">
				  	<div class="form-row mb-3">
				  		<div class="col">
				    	 	<label for="email">Email adresa</label>
							<input type="email" v-model="novaMedSestra.email" class="form-control" id="email" placeholder="Email" required>
							<div class="invalid-feedback" id="dodavanjeInvalid">Neispravan email.</div>
						</div>
					</div>
					<div class="form-row">
				    	<div class="col">
				    	 	<label for="lozinka1">Lozinka</label>
							<input type="password" minlength="8" maxlength="20" v-model="novaMedSestra.lozinka" class="form-control" id="lozinka1" placeholder="Lozinka" required>
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
							<input type="text" v-model="novaMedSestra.ime" class="form-control" id="ime" placeholder="Ime" required>
							<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli ime.</div>
						</div>
				    	<div class="col">
				    		<label for="prezime" class="mt-1">Prezime</label>
							<input type="text" v-model="novaMedSestra.prezime" class="form-control" id="prezime" placeholder="Prezime" required>
				    		<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli prezime.</div>
				    	</div>
				    	<div class="col">
				    		<label for="adresa" class="mt-1">Adresa</label>
							<input type="text" v-model="novaMedSestra.adresa" class="form-control" id="adresa" placeholder="Adresa" required>
				    		<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli adresu.</div>
				    	</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
				    		<label for="grad" class="mt-1">Grad</label>
							<input type="text" v-model="novaMedSestra.grad" class="form-control" id="grad" placeholder="Grad" required>
				    		<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli grad.</div>
				    	</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
				    		<label for="drzava" class="mt-1">Drzava</label>
							<input type="text" v-model="novaMedSestra.drzava" class="form-control" id="drzava" placeholder="Drzava" required>
				    		<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli državu.</div>
				    	</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
				    		<label for="tel" class="mt-1">Broj telefona</label>
							<input type="text" v-model="novaMedSestra.brojTelefona" class="form-control" id="tel" placeholder="Broj telefona" required>
				    		<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli broj telefona.</div>
				    	</div>
				  	</div>
				  	<div class="form-row">
				  		<div class="col">
							<label for="poc" class="mt-1">Početak radnog vremena</label>
							<div class="input-group clockpicker">
								<div class="input-group-prepend">
					            	<span class="input-group-text"><i class="material-icons">
										access_time
									</i></span>
								</div>
								<input class="form-control" id="poc" type="text" placeholder="Početak radnog vremena" required>
								<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli početak radnog vremena.</div>
					        </div>
						</div>
						<div class="col">
							<label for="kraj" class="mt-1">Kraj radnog vremena</label>
							<div class="input-group clockpicker">
								<div class="input-group-prepend">
					            	<span class="input-group-text"><i class="material-icons">
										access_time
									</i></span>
								</div>
								<input class="form-control" id="kraj" type="text" placeholder="Kraj radnog vremena" required>
								<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli kraj radnog vremena.</div>
					        </div>
						</div>
				  	</div>
				  	<div v-if=zauzetEmail class="alert alert-danger" role="alert">
						<p class="mb-0"><b>Greška!</b> Već postoji korisnik sa unetim Email-om. Pokušajte ponovo.</p>
					</div>
				  	<button class="btn btn-lg btn-primary btn-block mt-4" type="submit">
				  		Dodaj
				  	</button>
				</form>
				<router-link :to="{ name: 'medSestre' }" class="btn btn-secondary">Nazad</router-link>
			</div>
		</div>
	</div>
	`
	,
	methods : {
		dodajMedSestru : function () {
			this.proveriLozinke();
			this.submitovano = true;
			if (document.getElementById('forma-dodaj-medSestru').checkValidity() === true && this.poklapajuSeLozinke) {
				this.pocetakRadnog = $('#poc').val();
				this.krajRadnog = $('#kraj').val();
				axios
				.get('/korisnici/proveriEmail/' + this.novaMedSestra.email, { headers: { Authorization: 'Bearer ' + this.token }} )
				.then(response => {
					axios
					.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
			        .then(response => { 
			        	let ulogovan=response.data;
			        	axios.get('/adminiKlinike/ucitajKlinikuPoIDAdmina/'+ulogovan.id, { headers: { Authorization: 'Bearer ' + this.token }} )
			        	.then(response=>{
			        		let klinika=response.data;
			        		this.novaMedSestra.klinika=klinika.naziv;
			        		this.novaMedSestra.pocetakRadnogVremena = this.pretvori(this.pocetakRadnog);
			        		this.novaMedSestra.krajRadnogVremena = this.pretvori(this.krajRadnog);
			        		axios
							.post('sestre', this.novaMedSestra, { headers: { Authorization: 'Bearer ' + this.token }} )
							.then(response => {
								this.uspesnoDodavanje = response.data;
								
								if (this.uspesnoDodavanje) {
									this.$router.replace({ name: 'medSestre' });
								}
							})
							.catch(error => {
								console.log(error);
								this.uspesnoDodavanje = false;
							});
			        	})
			        })
			        .catch(function (error) { console.log(error); });
				})
				.catch(error => {
					console.log(error);
					this.uspesnoDodavanje = false;
					this.zauzetEmail = true;
				});
			} else {
				this.uspesnoDodavanje = true;
			}
		},
		proveriLozinke : function () {
			if (this.novaMedSestra.lozinka != this.potvrdaLozinke) {
				this.poklapajuSeLozinke = false;
			} else {
				this.poklapajuSeLozinke = true;
			}
		},
		pretvori : function (datumStr) {
			var sati = datumStr.substr(0, 2);
			var minuti = datumStr.substr(3, 2);
			return sati*3600000 + minuti*60000;
		},
	},
	mounted() {
		this.token = localStorage.getItem("token");
		$('.clockpicker').clockpicker({
		    'default': 'now',
		    vibrate: true,
		    placement: "top",
		    align: "right",
		    autoclose: true,
		    twelvehour: false,
		    donetext: 'Gotovo',
	    });
	}
});