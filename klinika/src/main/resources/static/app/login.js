Vue.component("login", {
	data: function () {
	    return {
	    	kor : {},
	    	korisnik : {
	    		username : "",
	    		password : "",
	    	},
	    	submitovano : false,
	    	uspesanLogin : true,
	    	nijeAktiviran : false,
	    	jesteAktivanNijeVerifikovan : false,
	    	promenioLozinku : true,
	    	token : "",
	    	uloga : ""
	    }
	},
	template: `
<div class="text-center">
	<div class="container">
		<form class="form-signin needs-validation" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="login" id="forma-login">
			<img class="mb-3" src="./hospital.jpg" alt="" width="100" height="100">
			<h1 class="h3 mb-3 font-weight-normal">Ulogujte se</h1>
			<div class="input-group">
				<label for="inputEmail" class="sr-only">Email adresa</label>
				<input v-model="korisnik.username" type="email" id="inputEmail" class="form-control" placeholder="Email adresa" required>
				<div class="invalid-feedback">Niste ispravno uneli Email adresu.</div>
			</div>
			<div class="input-group">
				<label for="inputPassword" class="sr-only">Lozinka</label>
				<input v-model="korisnik.password" type="password" id="inputPassword" class="form-control" placeholder="Lozinka" required>
				<div class="invalid-feedback">Niste uneli lozinku.</div>
			</div>
			<div v-if=!uspesanLogin class="alert alert-danger" role="alert">
				<p class="mb-0"><b>Greška!</b> Pogrešna Email adresa ili pogrešna lozinka. Pokušajte ponovo.</p>
			</div>
			<div v-if=nijeAktiviran class="alert alert-danger" role="alert">
				<p class="mb-0"><b>Greška!</b> Administrator još uvek nije odobrio vaš zahtev za registracijom.</p>
			</div>
			<div v-if=jesteAktivanNijeVerifikovan class="alert alert-danger" role="alert">
				<p class="mb-0"><b>Greška!</b> Vaš profil još uvek nije aktiviran. Poslat vam je aktivacioni link, proverite Email.</p>
			</div>
			<button class="btn mb-2 btn-lg btn-primary btn-block" type="submit">Prijavite se</button>
			<p class="h7 mt-2 font-weight-normal">Nemate nalog? <router-link :to="{ name: 'registracija' }">Registrujte se.</router-link></p>
			<p class="mt-5 mb-3 text-muted">&copy;2020</p>
	    </form>
	</div>
</div>
`
	,
	methods : {
		login : function () {
			this.submitovano = true;
			if (document.getElementById('forma-login').checkValidity() === true) {
				axios
				.post('auth/login', this.korisnik)
				.then(response => (this.validiraj(response.data)))
				.catch(function (error) { 
					console.log(error);
					this.upsesanLogin = false;
				});
			} else {
				this.uspesanLogin = true;
				this.nijeAktiviran = false;
				this.jesteAktivanNijeVerifikovan = false;
			}
		},
		validiraj : function (korisnikToken) {
			this.token = korisnikToken.accessToken;
			if (this.token == null) {
				this.uspesanLogin = false;
				this.nijeAktiviran = false;
				this.jesteAktivanNijeVerifikovan = false;
				this.promenioLozinku = true;
			} else {
				axios
				.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
		        .then(response => { 
		        	this.kor = response.data;
		        	this.nijeAktiviran = !this.kor.aktiviran;
					this.jesteAktivanNijeVerifikovan = !this.kor.verifikovan && this.kor.aktiviran;
					this.promenioLozinku = this.kor.promenjenaLozinka;
					this.uspesanLogin = true;
					
					if (!this.nijeAktiviran && !this.jesteAktivanNijeVerifikovan && !this.promenioLozinku) {
						this.$router.replace({ name: 'promenaLozinke', params: { korisnikToken: this.token } });
					} 
					if (!this.nijeAktiviran && !this.jesteAktivanNijeVerifikovan && this.promenioLozinku) {
						axios
			    		.put('/korisnici/dobaviUlogu', this.kor, { headers: { Authorization: 'Bearer ' + this.token }} )
			            .then(response => {
			            	this.uloga = response.data;
			            	if (this.uloga == "ROLE_PACIJENT") {
			            		this.$router.replace({ name: 'zdravstveniKarton', params: { korisnikToken: this.token } });
			            	} else if (this.uloga == "ROLE_LEKAR") {
			            		this.$router.replace({ name: 'pacijenti', params: { korisnikToken: this.token } });
			            	} else if (this.uloga == "ROLE_MED_SESTRA") {
			            		this.$router.replace({ name: 'pacijenti', params: { korisnikToken: this.token } });
			            	} else if (this.uloga == "ROLE_ADMIN_KLINICKOG_CENTRA") {
			            		this.$router.replace({ name: 'zahteviRegistracija', params: { korisnikToken: this.token } });
			            	} else if (this.uloga == "ROLE_ADMIN_KLINIKE") {
			            		this.$router.replace({ name: 'lekari', params: { korisnikToken: this.token } });
			            	} 
			            })
			            .catch(function (error) { console.log(error); });
					}
		        })
		        .catch(function (error) { console.log(error); });
			}
		}
	}
});