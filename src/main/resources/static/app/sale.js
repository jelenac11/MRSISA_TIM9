Vue.component("sale", {
	data : function() {
		return {
			ulogovan: {},
			sale: [],
			novaSala: {},
			izabranaSala: {},
			submitovano: false,
	    	uspesnaIzmena: true,
			token: "",
			search: "",
		} 
	},
	template: `
	<div> 
		<navig-bar v-bind:token="this.token"></navig-bar>
		
		<div class="naviga tab-content">
			<div class="input-group">
				<input type="search" class="form-control col-4 ml-auto m-2" v-model="search"  placeholder="Naziv..."/>
			</div>
			<table class="table table-hover table-striped">
			  	<thead class="thead-light">
			    	<tr>
				      	<th scope="col" width="20%">Broj</th>
				      	<th scope="col" width="80%">Naziv</th>
				      	<th></th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="sala in filtriraneSale">
				      	<td width="30%" data-toggle="modal" data-target="#izmenaSaleModal" v-on:click="izaberiSalu(sala)">{{ sala.broj }}</td>
				      	<td width="60%" data-toggle="modal" data-target="#izmenaSaleModal" v-on:click="izaberiSalu(sala)">{{ sala.naziv }}</td>
				      	<td width="10%"><button class="btn btn-danger btn-sm" v-on:click="obrisiSalu(sala)" id="brisanjeSale">Ukloni</button></td>
			    	</tr>
			  	</tbody>
			</table>
			<router-link :to="{ name: 'dodavanjeSale' }" class="btn btn-primary btn-block btn-lg my-2 p-2" id="dodavanjeSale">Dodaj novu salu</router-link>
		</div>
		
		<div class="modal fade" id="izmenaSaleModal" tabindex="-1" role="dialog">
			<div class="modal-dialog modal-lg" role="document">
		    	<div class="modal-content">
		      		<div class="modal-header">
		        		<h5 class="modal-title">Podaci o sali</h5>
		        		<button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
		      		</div>
		      		<div class="modal-body">
		        		<form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="izmenaSale" id="forma-izmena-sale">
							<div class="form-row mb-3">
						  		<div class="col">
						    	 	<label for="brsale">Broj sale</label>
									<input type="number" v-model="novaSala.broj" min="1" class="form-control" id="brsale" placeholder="Broj sale" required>
									<div class="invalid-feedback" id="dodavanjeInvalid">Unesite ispravan broj sale.</div>
								</div>
							</div>
						  	<div class="form-row mb-3">
						  		<div class="col">
						    	 	<label for="naz">Naziv sale</label>
									<input type="text" v-model="novaSala.naziv" class="form-control" v-bind:class="{ 'is-invalid':submitovano}" id="naz" placeholder="Naziv sale" required>
									<div class="invalid-feedback" id="dodavanjeInvalid">Unesite ispravno naziv sale.</div>
								</div>
							</div>
						  	<button class="btn btn-lg btn-primary btn-block mt-4" type="submit" v-bind:disabled="novaSala.naziv == izabranaSala.naziv && novaSala.broj == izabranaSala.broj">
						  		Sačuvaj izmene
						  	</button>
						</form>
		      		</div>
		      		<div class="modal-footer">
		        		<button type="button" class="btn btn-secondary mr-auto" data-dismiss="modal">Nazad</button>
		      		</div>
		    	</div>
			</div>
		</div>
	</div>
	`
	,
	methods:{
		obrisiSalu: function(sala) {
			var hoceDaBrise = confirm("Da li ste sigurni da želite izbrisati salu " + sala.naziv + "?");
			if (hoceDaBrise == true) {
				axios
				.delete('sale/'+sala.id, { headers: { Authorization: 'Bearer ' + this.token }})
				.then(response => {
					if (response.data==true) {
						toast("Uspešno izbrisana sala!");
						this.dobaviSve();
					} else {
						toast("Nije moguće izbrisati salu!")
						this.dobaviSve();
					}
				})
		        .catch(function (error) { console.log(error); });
			}
		},
		izaberiSalu: function(sala){
			this.novaSala = JSON.parse(JSON.stringify(sala));
			this.izabranaSala = JSON.parse(JSON.stringify(sala));
		},
		izmenaSale : function () {
			this.submitovano = true;
			if (document.getElementById('forma-izmena-sale').checkValidity() === true) {
				axios
				.post('/sale/izmenaSale', this.novaSala, { headers: { Authorization: 'Bearer ' + this.token }})
				.then(response => {
					this.submitovano = false;
					this.uspesnaIzmena = response.data;
					if (this.uspesnaIzmena) {
						toast("Uspešno izmenjena sala " + this.novaSala.naziv + ".");
						this.izabranaSala = JSON.parse(JSON.stringify(this.novaSala));
						this.dobaviSve();
					} else {
						toast("Salu nije moguće izmeniti.");
					}
				})
				.catch(error => {
					console.log(error);
					this.uspesnaIzmena = false;
				});
			} else {
				this.uspesnaIzmena = true;
			}
		},
		dobaviSve : function() {
			axios
	        .get('/sale/ucitajSve/'+this.ulogovan.id, { headers: { Authorization: 'Bearer ' + this.token }} )
	        .then(response => (this.sale = response.data))
	        .catch(function (error) { console.log(error); });
		},
	},
	created() {
		this.token = localStorage.getItem("token");
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { 
        	this.ulogovan=response.data
        	this.dobaviSve();
        })
        .catch(function (error) { console.log(error); });
		
	},
	computed : {
		filtriraneSale : function() {
			return this.sale.filter(sala => {
				if (this.search=="") {
					return true;
				}
				if (sala.naziv.toLowerCase().includes(this.search.toLowerCase())) {
					return true;
				} else {
					return false;
				}
			})
		}
	}
});