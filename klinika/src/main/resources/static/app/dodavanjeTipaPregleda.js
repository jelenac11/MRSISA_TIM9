Vue.component("dodavanje-tipaPregleda", {
	data : function() {
		return {
			noviTipPregleda : {
				naziv: "",
				opis: ""
			},
	    	submitovano : false,
	    	uspesnoDodavanje : true,
	    }
	},
	template: `
	<div class="container d-flex justify-content-center">
		<div class="card mt-5" style="width: 47rem;">
			<h4 class="card-header">Novi tip pregleda</h4>
			<div class="card-body">
				<form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="dodajTipPregleda" id="forma-dodaj-tipPregleda">
				  	<div class="form-row mb-3">
				  		<div class="col">
				    	 	<label for="tipPregleda">Naziv tipa pregleda</label>
							<input type="text" v-model="noviTipPregleda.naziv" class="form-control" id="nazivTipaPregleda" placeholder="Naziv tipa pregleda" required>
							<div class="invalid-feedback" id="dodavanjeInvalid">Unesite ispravan naziv tipa pregleda.</div>
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
				<router-link :to="{ path: 'tipoviPregleda' }" class="btn btn-secondary">Nazad</router-link>
			</div>
		</div>
	</div>
	`
	,
	methods : {
		dodajTipPregleda : function () {
			this.submitovano = true;
			
			if (document.getElementById('forma-dodaj-tipPregleda').checkValidity() === true) {
				axios
				.post('/tipoviPregleda', this.noviTipPregleda)
				.then(response => {
					this.uspesnoDodavanje = response.data;
					
					if (this.uspesnoDodavanje) {
						this.$router.replace({ path: 'tipoviPregleda' });
					}
				})
				.catch(error => {
					console.log(error);
					this.uspesnoDodavanje = false;
				});
			} else {
				this.uspesnoDodavanje = false;
			}
		},
	}
});