Vue.component("dodavanje-tipaPregleda", {
	data : function() {
		return {
			noviTipPregleda : {
				naziv: "",
				opis: "",
				klinika: "",
				cena: 0,
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
				    	 	<label for="cena">Cena</label>
							<input type="number" v-model="noviTipPregleda.cena" min="0" step="1" class="form-control" id="cena" placeholder="Cena">
							<div class="invalid-feedback" id="izmenaInvalid">Unesite ispravnu cenu.</div>
						</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
				    	 	<label for="inf">Opis</label>
							<input type="text" v-model="noviTipPregleda.opis" class="form-control" id="opis" placeholder="Opis"></input>
						</div>
				  	</div>
				  	<button style="color:white" class="btn btn-lg btn-primary btn-block mt-4" type="submit">
				  		Dodaj
				  	</button>
				</form>
				<router-link :to="{ name: 'tipoviPregleda' }" class="btn btn-secondary">Nazad</router-link>
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
					.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
			        .then(response => { 
			        	let ulogovan=response.data;
			        	axios.get('/adminiKlinike/ucitajKlinikuPoIDAdmina/'+ulogovan.id, { headers: { Authorization: 'Bearer ' + this.token }} )
			        	.then(response=>{
			        		let klinika=response.data;
			        		this.noviTipPregleda.klinika=klinika.naziv;
							axios
							.post('/tipoviPregleda', this.noviTipPregleda,{ headers: { Authorization: 'Bearer ' + this.token }})
							.then(response => {
								this.uspesnoDodavanje = response.data;
								
								if (this.uspesnoDodavanje) {
									this.$router.replace({ name: 'tipoviPregleda' });
								}
							})
							.catch(error => {
								console.log(error);
								this.uspesnoDodavanje = false;
							});
			        	});
			        })
			        .catch(function (error) { console.log(error); });
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
		this.token = localStorage.getItem("token");
	}
});