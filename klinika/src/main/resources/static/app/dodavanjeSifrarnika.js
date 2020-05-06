Vue.component("dodavanje-sifrarnika", {
	data : function() {
		return {
			noviSifrarnik : {
				naziv: "",
				tipSifrarnika: "",
			},
	    	submitovano : false,
	    	uspesnoDodavanje : true,
	    	token : "",
	    }
	},
	template: `
	<div class="container d-flex justify-content-center">
		<div class="card mt-5" style="width: 47rem;">
			<h4 class="card-header">Novi šifrarnik</h4>
			<div class="card-body">
				<form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="dodajSifrarnik" id="forma-dodaj-sifrarnik">
				  	<div class="form-row mb-3">
				  		<div class="col">
				    	 	<label for="naz">Naziv</label>
							<input type="text" v-model="noviSifrarnik.naziv" class="form-control" id="naz" placeholder="Naziv" required></input>
							<div class="invalid-feedback" id="dodavanjeInvalid">Niste uneli naziv sifrarnika.</div>
						</div>
					</div>
				  	<div class="form-row">
				    	<div class="col">
				    		<label for="tip" class="mt-1">Tip šifrarnika</label>
				    		<select class="custom-select mt-0" v-model="noviSifrarnik.tipSifrarnika" id="tip" required>
						    	<option value="Sifrarnik dijagnoza">Sifrarnik lekova</option>
						    	<option value="Sifrarnik lekova">Sifrarnik dijagnoza</option>
						  	</select>
			    		<div class="invalid-feedback" id="dodavanjeInvalid">Niste odabrali tip šifrarnika.</div>
			    	</div>
				  	</div>
				  	<button class="btn btn-lg btn-primary btn-block mt-4" type="submit">
				  		Dodaj
				  	</button>
				</form>
				<router-link :to="{ name: 'sifrarnici' }" class="btn btn-secondary">Nazad</router-link>
			</div>
		</div>
	</div>
	`
	,
	methods : {
		dodajSifrarnik : function () {
			this.submitovano = true;
			if (document.getElementById('forma-dodaj-sifrarnik').checkValidity() === true) {
				axios
				.post('/sifrarnici', this.noviSifrarnik, { headers: { Authorization: 'Bearer ' + this.token }} )
				.then(response => {
					this.uspesnoDodavanje = response.data;
					
					if (this.uspesnoDodavanje) {
						this.$router.replace({ name: 'sifrarnici' });
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
	},
	mounted() {
		this.token = localStorage.getItem("token");
	}
});