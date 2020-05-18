Vue.component("zdravstveni-karton", {
	data : function() {
		return {
			ulogovan: {},
			zdravstveniKarton: {},
			izabraniIzvestaj: {
				opis: "",
				dijagnoza: {},
				recepti: [],
				pregled: {},
			},
			noviIzvestaj: {
				opis: "",
				dijagnoza: {},
				recepti: [],
				pregled: {},
			},
			imaBolesti: false,
			token: "",
		} 
	},
	template: `
	<div> 
		<navig-bar v-bind:token="this.token"></navig-bar>
		<div class="naviga">
			<div class="container d-flex justify-content-center">
				<div class="card mt-5" style="width: 70rem;">
					<h4 class="card-header">Zdravstveni karton</h4>
					<div class="card-body">
						<ul class="list-group">
						  	<li class="list-group-item">
						  		<div class="d-flex w-20 justify-content-between">
							  		<h6>Ime:</h6>
							  		<p class="mb-0">{{ this.ulogovan.ime }}</p>
							  	</div>
							  	<div class="d-flex w-20 justify-content-between">
							  		<h6>Prezime:</h6>
							  		<p class="mb-0">{{ this.ulogovan.prezime }}</p>
							  	</div>
						  	</li>
						  	<li class="list-group-item">
						  		<div class="d-flex w-20 justify-content-between">
							  		<h6>Visina:</h6>
							  		<p class="mb-0">{{ this.zdravstveniKarton.visina }}</p>
							  	</div>
							  	<div class="d-flex w-20 justify-content-between">
							  		<h6>Težina:</h6>
							  		<p class="mb-0">{{ this.zdravstveniKarton.tezina }}</p>
							  	</div>
						  	</li>
						  	<li class="list-group-item">
						  		<div class="d-flex w-20 justify-content-between">
						  			<h6>Krvna grupa:</h6>
						  			<p class="mb-0">{{ this.zdravstveniKarton.krvnaGrupa }}</p>
						  		</div>
						  		<div class="d-flex w-20 justify-content-between">
						  			<h6>Dioptrija:</h6>
						  			<p class="mb-0">{{ this.zdravstveniKarton.dioptrija }}</p>
						  		</div>
						  	</li>
						  	<li class="list-group-item">
						  		<div class="w-20 justify-content-between">
						    		<h6>Izveštaji:</h6>
									<table v-if="imaBolesti" class="table table-hover table-striped mt-2">
									  	<thead class="thead-light">
									    	<tr>
									    		<th scope="col" width="33%">Tip pregleda</th>
										      	<th scope="col" width="33%">Lekar</th>
										      	<th scope="col" width="34%">Datum</th>
									    	</tr>
									  	</thead>
									  	<tbody>
									  		<tr v-for="iz in zdravstveniKarton.bolesti" data-toggle="modal" data-target="#prikaziIzvestaj" v-on:click="izaberiIzvestaj(iz)">
									    		<th class="h6" scope="col" width="33%">{{ iz.pregled.tipPregleda.naziv }}</th>
									    		<th class="h6" scope="col" width="33%">{{ iz.pregled.lekar.ime + " " + iz.pregled.lekar.prezime }}</th>
									    		<th class="h6" scope="col" width="34%">{{ urediDatum(iz.pregled.vreme2) }}</th>
									    	</tr>
									  	</tbody>
									</table>
								</div>
					  		</li>
						</ul>
					</div>
				</div>
			</div>
			
			<div class="modal fade" id="prikaziIzvestaj" tabindex="-1" role="dialog">
				<div class="modal-dialog modal-lg" role="document">
			    	<div class="modal-content">
			      		<div class="modal-header">
			        		<h5 class="modal-title">Izveštaj o pregledu</h5>
			        		<button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
			      		</div>
			      		<div class="modal-body">
							<div class="d-flex">
								<p class="h5 m-2">Informacije: {{ izabraniIzvestaj.opis }}</p>
							</div>
							<div class="d-flex">
								<p class="h5 m-2">Dijagnoza: {{ izabraniIzvestaj.dijagnoza.naziv }}</p>
							</div>
							<p class="h3 mt-4 ml-2 font-weight-normal">Recepti</p>
							<table class="table table-hover table-striped">
							  	<thead class="thead-light">
							    	<tr>
								      	<th scope="col" width="50%">Šifra</th>
								      	<th scope="col" width="50%">Naziv leka</th>
							    	</tr>
							  	</thead>
							  	<tbody>
							  		<tr v-for="rec in izabraniIzvestaj.recepti" data-toggle="modal" data-target="#" v-on:click="">
								      	<td width="50%">{{ rec.lek.sifra }}</td>
								      	<td width="50%">{{ rec.lek.naziv }}</td>
							    	</tr>
							  	</tbody>
							</table>
			      		</div>
			      		<div class="modal-footer">
			        		<button type="button" class="btn btn-secondary mr-auto" data-dismiss="modal">Nazad</button>
			      		</div>
			    	</div>
				</div>
			</div>
		
		</div>
	</div>
	`
	,
	methods: {
		izaberiIzvestaj : function (izv) {
			this.izabraniIzvestaj = izv;
			this.noviIzvestaj = JSON.parse(JSON.stringify(this.izabraniIzvestaj));
		},
		urediDatum: function(datum){
	        var date = new Date(datum);
	        datum = date.toLocaleDateString('en-GB', {
	        day: 'numeric', month: 'short', year: 'numeric'
	        }).replace(/ /g, '-');
	        vreme = date.toLocaleTimeString();
	        return datum + " " + vreme
		},
	},
	created() {
		this.token = localStorage.getItem("token");
	},
	mounted() {
		axios
		.get('/auth/dobaviUlogovanog', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => { 
        	this.ulogovan = response.data;
        	axios
    		.put('/zdravstveniKartoni/dobaviKartonPacijenta', this.ulogovan, { headers: { Authorization: 'Bearer ' + this.token }} )
            .then(response => { 
            	this.zdravstveniKarton = response.data;
            	var nema = this.zdravstveniKarton.bolesti === undefined || this.zdravstveniKarton.bolesti.length == 0;
    			this.imaBolesti = !nema;
            })
            .catch(function (error) { console.log(error); });
        })
        .catch(function (error) { console.log(error); });
	}
});