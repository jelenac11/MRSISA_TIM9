Vue.component("dodavanje-tipaPregleda", {
	data : function() {
		return {
			noviTipPregleda : {
				naziv: "",
				opis: ""
			},
	    	submitovano : false,
	    	uspesnoDodavanje : true,
	    	jedinstvenoIme : true,
	    	token : "",
	    }
	},
	template: `
	<div class="container d-flex justify-content-center">
		<div class="card mt-5" style="width: 47rem;">
			<h4 class="card-header">Novi tip pregleda</h4>
			<div class="card-body">
				<form class="needs-validation mb-4" v-on:submit="dodajTipPregleda" novalidate id="forma-dodaj-tipPregleda">
				  	<div class="form-row mb-3">
				  		<div class="col">
				    	 	<label for="tipPregleda">Naziv tipa pregleda</label>
							<input type="text" v-model="noviTipPregleda.naziv" class="form-control" v-bind:class="{ 'is-invalid': !jedinstvenoIme && submitovano}" id="nazivTipaPregleda" placeholder="Naziv tipa pregleda" required>
							<div class="invalid-feedback" id="dodavanjeInvalid">Uneti naziv tipa pregleda je neispravan ili zauzet.</div>
						</div>
					</div>
				  	<div class="form-row">
				    	<div class="col">
				    	 	<label for="inf">Opis</label>
							<input type="text" v-model="noviTipPregleda.opis" class="form-control" id="opis" placeholder="Opis"></input>
						</div>
				  	</div>
				  	<button class="btn btn-lg btn-primary btn-block mt-4" type="submit">
				  		Dodaj
				  	</button>
				</form>
				<router-link :to="{ name: 'tipoviPregleda', params: { korisnikToken: this.token } }" class="btn btn-secondary">Nazad</router-link>
			</div>
		</div>
	</div>
	`
	,
	methods : {
		dodajTipPregleda : function (event) {
			this.submitovano = true;
			event.preventDefault();
			this.provjeraImena();
		},
		provjeraImena: function(){
			if (this.noviTipPregleda.naziv === "") {
				this.jedinstvenoIme=false;
			}
			else {
			axios
			.get("/tipoviPregleda/provjeraPostojanostiImena/" + this.noviTipPregleda.naziv, { headers: { Authorization: 'Bearer ' + this.token }} )
			.then(response=> {
				if (!response.data) {
					axios
					.post('/tipoviPregleda', this.noviTipPregleda)
					.then(response => {
						this.uspesnoDodavanje = response.data;
						
						if (this.uspesnoDodavanje) {
							this.$router.replace({ name: 'tipoviPregleda', params: { korisnikToken: this.token } });
						}
					})
					.catch(error => {
						console.log(error);
						this.uspesnoDodavanje = false;
					});
				} else {
					this.jedinstvenoIme = false;
				}})
				.catch(response=>{
					this.jedinstvenoIme = false;
				})
			}
		}
	},
	mounted() {
		this.token = this.$route.params.korisnikToken;
	}
});