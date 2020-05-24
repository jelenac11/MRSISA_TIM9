Vue.component("promena-lozinke", {
	data : function() {
		return {
			ulogovan : {},
	    	submitovano : false,
	    	lozinka : "",
	    	novaLozinka : "",
	    	potvrdaLozinke : "",
	    	poklapajuSeLozinke : true,
	    	token : "",
	    }
	},
	template: `
	<div class="container d-flex justify-content-center">
		<div class="card mt-5" style="width: 47rem;">
			<h4 class="card-header">Nova lozinka</h4>
			<div class="card-body">
				<form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="promeniLozinku" id="forma-promeni-lozinku">
				  	<div class="form-row mb-3">
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
				  	<button class="btn btn-lg btn-primary btn-block mt-4" type="submit">
				  		Sačuvaj izmene
				  	</button>
				</form>
			</div>
		</div>
	</div>
	`
	,
	methods : {
		promeniLozinku : function () {
			this.proveriLozinke();
			this.submitovano = true;
			if (document.getElementById('forma-promeni-lozinku').checkValidity() === true && this.poklapajuSeLozinke) {
				this.ulogovan.promenjenaLozinka = true;
				axios
				.put('/korisnici', this.ulogovan, { headers: { Authorization: 'Bearer ' + this.token }} )
				.then(response => { this.$router.replace({ name: 'login' }); })
				.catch(function (error) { console.log(error); });
			}
		},
		proveriLozinke : function () {
			if (this.novaLozinka != "") {
				if (this.novaLozinka != this.potvrdaLozinke) {
					this.poklapajuSeLozinke = false;
				} else {
					this.poklapajuSeLozinke = true;
					this.ulogovan.lozinka = this.novaLozinka;
				}
			} else {
				this.poklapajuSeLozinke = true;
				this.ulogovan.lozinka = this.novaLozinka;
			}
		},
	},
	mounted() {
		this.token = localStorage.getItem("token");
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { this.ulogovan = response.data; })
        .catch(function (error) { console.log(error); });
	}
});