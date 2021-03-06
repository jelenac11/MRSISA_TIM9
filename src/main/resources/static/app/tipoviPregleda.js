Vue.component("tipoviPregleda", {
	data : function() {
		return {
			ulogovan: {},
			tipoviPregleda: [],
			noviTipPregleda:{},
			izabraniTipPregleda:{},
			submitovano : false,
	    	uspesnaIzmena : true,
	    	token: "",
	    	search:'',
		} 
	},
	template: `
	<div> 
		<navig-bar v-bind:token="this.token"></navig-bar>
		<div class="naviga tab-content">
			<div class="input-group">
				<input type="search" class="form-control col-4 ml-auto m-2" v-model="search"  placeholder="Pretraga..."/>
			</div>
			<table class="table table-hover table-striped">
			  	<thead class="thead-light">
			    	<tr>
				      	<th scope="col" width="30%">Naziv</th>
				      	<th scope="col" width="50%">Opis</th>
				      	<th scope="col" width="20%"></th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="tipPregleda in filtriraniTipovi">
				      	<td width="30%" data-toggle="modal" data-target="#izmenaTipaPregledaModal"  v-on:click="izaberiTipPregleda(tipPregleda)">{{ tipPregleda.naziv }}</td>
				      	<td width="50%" data-toggle="modal" data-target="#izmenaTipaPregledaModal"  v-on:click="izaberiTipPregleda(tipPregleda)">{{ tipPregleda.opis }}</td>
				      	<td width="20%"><button class="btn btn-danger btn-sm" v-on:click="obrisiTipPregleda(tipPregleda)" id="brisanjeTipa">Ukloni</button></td>
			    	</tr>
			  	</tbody>
			</table>
			<router-link :to="{ name: 'dodavanjeTipaPregleda' }" class="btn btn-primary btn-block btn-lg my-2 p-2" id="dodavanjeTipaPregleda">Dodaj novi tip pregleda</router-link>
		</div>
		
		<div class="modal fade" id="izmenaTipaPregledaModal" tabindex="-1" role="dialog">
			<div class="modal-dialog modal-lg" role="document">
		    	<div class="modal-content">
		      		<div class="modal-header">
		        		<h5 class="modal-title">Podaci o tipu pregleda</h5>
		        		<button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
		      		</div>
		      		<div class="modal-body">
		        		<form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="izmenaTipaPregleda" id="forma-izmena-tipaPregleda">
						  	
						  	<div class="form-row mb-3">
						  		<div class="col">
						    	 	<label for="tipPregleda">Naziv tipa pregleda</label>
									<input type="text" v-model="noviTipPregleda.naziv" class="form-control" v-bind:class="{ 'is-invalid':submitovano}" id="nazivTipaPregleda" placeholder="Naziv tipa pregleda" required>
									<div class="invalid-feedback" id="dodavanjeInvalid">Uneti naziv tipa pregleda je neispravan.</div>
								</div>
							</div>
						  	<div class="form-row">
						    	<div class="col">
						    	 	<label for="inf">Opis</label>
									<input type="text" v-model="noviTipPregleda.opis" class="form-control" id="opis" placeholder="Opis"></input>
								</div>
						  	</div>
						  	<div class="form-row">
						    	<div class="col">
						    	 	<label for="cena">Cena</label>
									<input type="number" v-model="noviTipPregleda.cena" min="0" step="1" class="form-control" id="cena" placeholder="Cena">
									<div class="invalid-feedback" id="izmenaInvalid">Unesite ispravnu cenu.</div>
								</div>
						  	</div>
						  	<button style="color:white" class="btn btn-lg btn-primary btn-block mt-4" type="submit" v-bind:disabled="noviTipPregleda.naziv==izabraniTipPregleda.naziv && noviTipPregleda.opis==izabraniTipPregleda.opis && noviTipPregleda.cena==izabraniTipPregleda.cena">
						  		Sačuvaj izmene
						  	</button>
						</form>
		      		</div>
		      		<div class="modal-footer">
		        		<button style="color:white" type="button" class="btn btn-secondary mr-auto" data-dismiss="modal">Nazad</button>
		      		</div>
		    	</div>
			</div>
		</div>
		
	</div>
	`
	,
	created() {
		this.token = localStorage.getItem("token");
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { 
        	this.korisnik=response.data
        	this.dobaviSve();
        })
        .catch(function (error) { console.log(error); });
	},
	methods:{
		obrisiTipPregleda: function(tipPregleda){
			var hoceDaBrise = confirm("Da li ste sigurni da želite izbrisati tip pregleda "+ tipPregleda.naziv +"?");
			if (hoceDaBrise == true) {
				axios
				.post('tipoviPregleda/izbrisiTipPregleda', tipPregleda, { headers: { Authorization: 'Bearer ' + this.token }})
				.then(response => {
					if(response.data==true){
						toast("Uspešno izbrisan tip pregleda "+tipPregleda.naziv+".");
						this.dobaviSve();
					}
					else{
						toast("Tip pregleda nije moguce izbrisati.");
						this.dobaviSve();
					}
				})
				.catch(function (error) { console.log(error); });
			}

		},
		dobaviSve:function(){
			axios
            .get('/tipoviPregleda/ucitajSvePoIdKlinike/'+this.korisnik.id, { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => (this.tipoviPregleda = response.data))
            .catch(function (error) { console.log(error); });
		},
		izmenaTipaPregleda : function () {
			if (document.getElementById('forma-izmena-tipaPregleda').checkValidity() === true) {
				axios
				.post('/tipoviPregleda/izmenaTipaPregleda', this.noviTipPregleda, { headers: { Authorization: 'Bearer ' + this.token }})
				.then(response => {
					this.submitovano=false;
					this.uspesnaIzmena = response.data;
					if (this.uspesnaIzmena) {
						toast("Uspešno izmenjen tip pregleda "+ this.noviTipPregleda.naziv + ".");
						this.izabraniTipPregleda = JSON.parse(JSON.stringify(this.noviTipPregleda));
						this.dobaviSve();
					}
					else{
						toast("Tip pregleda nije moguce izmeniti.");
					}
				})
				.catch(error => {
					console.log(error);
					this.uspesnaIzmena = false;
				});
			} else {
				this.uspesnaIzmena = true;
			}
			this.submitovano = true;
		},
		izaberiTipPregleda: function(tipPregleda){
			this.noviTipPregleda=JSON.parse(JSON.stringify(tipPregleda));
			this.izabraniTipPregleda=JSON.parse(JSON.stringify(tipPregleda));
		}
	},
	computed:{
		filtriraniTipovi: function(){
			return this.tipoviPregleda.filter(tipPregleda => {
				if(this.search==""){
					return true;
				}
				if(tipPregleda.naziv.toLowerCase().includes(this.search.toLowerCase()) || tipPregleda.opis.toLowerCase().includes(this.search.toLowerCase()))
				{
					return true;
				}
				else{
					return false;
				}
			})
		}
	}
});