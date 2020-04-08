Vue.component("dodavanje-sale", {
	data : function() {
		return {
			novaSala : {
				broj: 0,
				info: ""
			},
	    	submitovano : false,
	    	uspesnoDodavanje : true,
	    }
	},
	template: `
	<div class="container d-flex justify-content-center">
		<div class="card mt-5" style="width: 47rem;">
			<h4 class="card-header">Nova sala</h4>
			<div class="card-body">
				<form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="dodajSalu" id="forma-dodaj-salu">
				  	<div class="form-row mb-3">
				  		<div class="col">
				    	 	<label for="brsale">Broj sale</label>
							<input type="number" v-model="novaSala.broj" min="1" class="form-control" id="brsale" placeholder="Broj sale" required>
							<div class="invalid-feedback" id="dodavanjeInvalid">Unesite ispravan broj sale.</div>
						</div>
					</div>
				  	<div class="form-row">
				    	<div class="col">
				    	 	<label for="inf">Dodatne informacije</label>
							<textarea v-model="novaSala.info" class="form-control" id="inf" placeholder="Dodatne informacije" rows="3"></textarea>
						</div>
				  	</div>
				  	<button class="btn btn-lg btn-primary btn-block mt-4" type="submit">
				  		Dodaj
				  	</button>
				</form>
				<router-link :to="{ path: 'sale' }" class="btn btn-secondary">Nazad</router-link>
			</div>
		</div>
	</div>
	`
	,
	methods : {
		dodajSalu : function () {
			this.submitovano = true;
			if (document.getElementById('forma-dodaj-salu').checkValidity() === true) {
				axios
				.post('/dodajSalu', this.novaSala)
				.then(response => {
					this.uspesnoDodavanje = response.data;
					
					if (this.uspesnoDodavanje) {
						this.$router.replace({ path: 'sale' });
					}
				})
				.catch(error => {
					console.log(error);
					this.uspesnoDodavanje = false;
				});
			} else {
				this.uspesnoDodavanje = true;
			}
		}
	}
});