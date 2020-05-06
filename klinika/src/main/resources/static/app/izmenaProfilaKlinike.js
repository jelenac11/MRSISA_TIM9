Vue.component("izmena-profilaKlinike", {
	data : function() {
		 return {
			klinika : {} ,
	    	korisnik : {},
	    	izmenjenaKlinika : {},
	    	submitovano : false,
	    	uspesnaIzmena : true,
	    	token : "",
	    	uspesno: true,
	    }
	},
	template: `
	<div class="container d-flex justify-content-center">
		<div class="card mt-5" style="width: 70rem;">
			<h4 class="card-header">Izmena podataka klinike</h4>
			<div class="card-body">
				<form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="izmenaPod" id="forma-izmena">
					<div class="form-row">
						<div class="col">
				    	 	<label for="naziv">Naziv</label>
							<input type="text" v-model="izmenjenaKlinika.naziv" class="form-control" id="naziv" v-bind:class="{ 'is-invalid': !uspesno}" placeholder="Naziv" required>
							<div class="invalid-feedback" id="izmenaInvalid">Uneti naziv je neispravan ili zauzet.</div>
						</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
				    	 	<label for="lokacija" class="mt-1">Lokacija</label>
							<input type="text" v-model="izmenjenaKlinika.lokacija" class="form-control" id="lokacija" placeholder="Lokacija" required>
							<div class="invalid-feedback" id="izmenaInvalid">Niste uneli lokaciju.</div>
						</div>
				    </div>
				    <div class="form-row">
				    	<div class="col">
				    		<label for="opis" class="mt-1">Opis</label>
							<input type="text" v-model="izmenjenaKlinika.opis" class="form-control" id="opis" placeholder="Opis">
				    	</div>
				  	<button class="btn btn-lg btn-primary btn-block mt-4" type="submit" v-bind:disabled="izmenjenaKlinika.naziv == klinika.naziv && izmenjenaKlinika.lokacija == klinika.lokacija && izmenjenaKlinika.opis == klinika.opis">
				  		Sačuvaj izmene
				  	</button>
				</form>
				<button class="btn btn-lg btn-secondary btn-block mt-4" v-on:click="nazad">Nazad</button>
			</div>
		</div>
	</div>	
	`
	,
	methods : {
		izmenaPod : function () {
			this.submitovano = true;
			if (document.getElementById('forma-izmena').checkValidity() === true) {
				axios
				.post('/klinike/izmenaProfilaKlinike', this.izmenjenaKlinika, { headers: { Authorization: 'Bearer ' + this.token }} )
				.then(response => {
					
					this.uspesno = response.data;
					if (this.uspesno) {
						this.uspesnaIzmena = true;
						toast("Uspešno izmenjeni podaci klinike.");
					}
					else{
						this.uspesnaIzmena = false;
						toast("Neuspešno izmenjeni podaci klinike.");
					}
				})
				.catch(function (error) { console.log(error);this.uspesnaIzmena = false; });
			} else {
				this.uspesnaIzmena = true;
			}
		},
		nazad : function () {
            this.$router.replace({ name: 'lekari', params: { korisnikToken: this.token } });
		},
	},
	mounted () {
		this.token = localStorage.getItem("token");
		this.klinika=this.$route.params.klinikaParam;
		this.izmenjenaKlinika=JSON.parse(JSON.stringify(this.klinika));
	}
});