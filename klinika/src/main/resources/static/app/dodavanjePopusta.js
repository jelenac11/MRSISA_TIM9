Vue.component("dodavanje-popusta", {
	data : function() {
		return {
			ulogovan : {},
			noviPopust : {
				tipPregleda : "",
				pocetak : 0,
				kraj : 0,
				procenat : 0,
			},
	    	submitovano : false,
	    	pocetak : null,
	    	kraj : null,
	    	uspesnoDodavanje : true,
	    	okejDatumi : true,
	    	tipovi : [],
	    	token : "",
	    }
	},
	template: `
	<div class="container d-flex justify-content-center">
		<div class="card mt-5" style="width: 47rem;">
			<h4 class="card-header">Novi popust</h4>
			<div class="card-body">
				<form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="dodajPopust" id="forma-dodaj-popust">
				  	<div class="form-row">
				    	<div class="col">
				    		<label for="tip" class="mt-1">Tip pregleda</label>
							<select class="custom-select mt-0" v-model="noviPopust.tipPregleda" id="tip" required>
						    	<option v-for="t in tipovi" :value="t.naziv">
									{{ t.naziv }}
						    	</option>
							</select>
							<div class="invalid-feedback">Niste izabrali tip pregleda.</div>
				    	</div>
				  	</div>
				  	<div class="form-row">
				    	<div class="col">
				    		<label for="poc" class="mt-1">Početak</label>
				    		<input type="date" v-model="pocetak" id="poc" placeholder="Početak" class="form-control" required>
				    		<div class="invalid-feedback">Niste uneli datum početka.</div>
				    	</div>
				    	<div class="col">
				    		<label for="kraj" class="mt-1">Kraj</label>
				    		<input type="date" v-model="kraj" id="kraj" placeholder="Kraj" class="form-control" required>
				    		<div class="invalid-feedback">Niste uneli datum kraja.</div>
				    	</div>
				  	</div>
				  	<div class="form-row mb-3">
				  		<div class="col">
				    	 	<label for="proc">Procenat</label>
							<input type="number" v-model="noviPopust.procenat" min="0" max="100" class="form-control" id="proc" placeholder="Procenat" required>
							<div class="invalid-feedback" id="dodavanjeInvalid">Unesite ispravno procenat (od 0 do 100).</div>
						</div>
					</div>
					<div v-if="!okejDatumi" class="alert alert-danger" role="alert">
						<p class="mb-0"><b>Greška!</b> Datum kraja ne sme biti pre datuma početka. Pokušajte ponovo.</p>
					</div>
				  	<button class="btn btn-lg btn-primary btn-block mt-4" type="submit">
				  		Dodaj
				  	</button>
				</form>
				<router-link :to="{ name: 'cenovnik' }" class="btn btn-secondary">Nazad</router-link>
			</div>
		</div>
	</div>
	`
	,
	methods : {
		dodajPopust : function () {
			this.okejDatumi = this.pocetak < this.kraj;
			this.submitovano = true;
			if (document.getElementById('forma-dodaj-popust').checkValidity() === true && this.okejDatumi) {
				this.noviPopust.pocetak = new Date(this.pocetak).getTime();
				this.noviPopust.kraj = new Date(this.kraj).getTime();
				axios
				.post('popusti/dodaj', this.noviPopust, { headers: { Authorization: 'Bearer ' + this.token }} )
				.then(response => {
					this.uspesnoDodavanje = response.data;
					
					if (this.uspesnoDodavanje) {
						this.$router.replace({ name: 'cenovnik' });
					}
				})
				.catch(error => {
					console.log(error);
					this.uspesnoDodavanje = false;
				});
			} else {
				this.uspesnoDodavanje = true;
				if (!(document.getElementById('forma-dodaj-popust').checkValidity() === true)) {
					this.okejDatumi = true;
				}
			}
		},
	},
	mounted() {
		this.token = localStorage.getItem("token");
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { 
        	this.ulogovan = response.data;
        	axios
            .get('/tipoviPregleda/ucitajSvePoIdKlinike/' + this.ulogovan.id, { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => (this.tipovi = response.data))
            .catch(function (error) { console.log(error); });
        })
        .catch(function (error) { console.log(error); });
	}
});