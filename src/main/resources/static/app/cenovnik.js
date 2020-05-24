Vue.component("cenovnik", {
	data : function() {
		return {
			cenovnik : {},
			popusti : [],
			novaStavka : {},
			submitovano : false,
			token : "",
			ulogovan : {},
			noviPopust : {
				tipPregleda : "",
				pocetak : 0,
				kraj : 0,
				procenat : 0,
			},
	    	submitovano2 : false,
	    	pocetak : null,
	    	kraj : null,
	    	okejDatumi : true,
	    	tipovi : [],
		} 
	},
	template: `
	<div> 
		<navig-bar v-bind:token="this.token"></navig-bar>
		
		<div class="naviga tab-content">
			<table class="table table-hover table-striped">
			  	<thead class="thead-light">
			    	<tr>
				      	<th scope="col" width="80%">Tip pregleda</th>
				      	<th scope="col" width="20%">Cena</th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="stavka in cenovnik.stavke" data-toggle="modal" data-target="#izmenaStavke" v-on:click="izaberiStavku(stavka)">
				      	<td width="80%">{{ stavka.tipPregleda }}</td>
				      	<td width="20%">{{ stavka.cena }}</td>
			    	</tr>
			  	</tbody>
			</table>
			
			<p class="h3 mt-4 ml-2 font-weight-normal">Popusti</p>
			<table class="table table-hover table-striped mt-2">
			  	<thead class="thead-light">
			    	<tr>
			    		<th scope="col" width="25%">Tip pregleda</th>
				      	<th scope="col" width="25%">Početak</th>
				      	<th scope="col" width="25%">Kraj</th>
				      	<th scope="col" width="25%">Popust</th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="pop in popusti" data-toggle="modal" data-target="#izmenaPopusta" v-on:click="izaberiPopust(pop)">
						<td width="25%">{{ pop.tipPregleda }}</td>
				      	<td width="25%">{{ datum(pop.pocetak) }}</td>
				      	<td width="25%">{{ datum(pop.kraj) }}</td>
				      	<td width="25%">{{ pop.procenat }}%</td>
			    	</tr>
			  	</tbody>
			</table>
			<router-link :to="{ name: 'dodavanjePopusta' }" class="btn btn-primary btn-block btn-lg my-2 p-2" id="dodavanjePopusta">Dodaj novi popust</router-link>
		</div>
		
		<div class="modal fade" id="izmenaStavke" tabindex="-1" role="dialog">
			<div class="modal-dialog modal-lg" role="document">
		    	<div class="modal-content">
		      		<div class="modal-header">
		        		<h5 class="modal-title">Stavka cenovnika</h5>
		        		<button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
		      		</div>
		      		<div class="modal-body">
		        		<form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="izmeniStavku" id="forma-izmeni-stavku">
						  	<div class="form-row">
						    	<div class="col">
						    	 	<label for="tip" class="mt-1">Tip pregleda</label>
									<input type="text" v-model="novaStavka.tipPregleda" class="form-control" id="tip" placeholder="Tip pregleda" disabled required>
								</div>
						  	</div>
						  	<div class="form-row">
						    	<div class="col">
						    	 	<label for="cena">Cena</label>
									<input type="number" v-model="novaStavka.cena" min="0" step="1" class="form-control" id="cena" placeholder="Cena">
									<div class="invalid-feedback" id="izmenaInvalid">Unesite ispravnu cenu.</div>
								</div>
						  	</div>
						  	<div class="form-row">
						    	<div class="col">
						    		<label class="mt-3" for="tabelaPopusta">Popusti</label>
						    		<table class="table table-hover" id="tabelaPopusta">
									  	<thead>
									    	<tr>
									      		<th scope="col">Početak</th>
									      		<th scope="col">Kraj</th>
										        <th scope="col">Popust</th>
									    	</tr>
									  	</thead>
									  	<tbody>
									    	<tr v-for="p in novaStavka.popusti">
										        <td>{{ datum(p.pocetak) }}</td>
										        <td>{{ datum(p.kraj) }}</td>
										        <td>{{ p.procenat }}%</td>
											</tr>
									  	</tbody>
									</table>
								</div>
						  	</div>
						  	<button class="btn btn-lg btn-primary btn-block mt-4" type="submit">
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
		
		<div class="modal fade" id="izmenaPopusta" tabindex="-1" role="dialog">
			<div class="modal-dialog modal-lg" role="document">
		    	<div class="modal-content">
		      		<div class="modal-header">
		        		<h5 class="modal-title">Popust</h5>
		        		<button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
		      		</div>
		      		<div class="modal-body">
		        		<form class="needs-validation mb-4" v-bind:class="{ 'was-validated': submitovano2 }" novalidate @submit.prevent="izmeniPopust" id="forma-izmeni-popust">
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
	methods : {
		dobaviSve : function () {
			axios
	        .get('cenovnici/ucitajCenovnik', { headers: { Authorization: 'Bearer ' + this.token }} )
	        .then(response => {
        		this.cenovnik = response.data;
        		this.cenovnik.stavke.sort(function (a, b) { return a.cena - b.cena; });
        	})
	        .catch(function (error) { console.log(error); });
			axios
	        .get('popusti/ucitajSve', { headers: { Authorization: 'Bearer ' + this.token }} )
	        .then(response => (this.popusti = response.data))
	        .catch(function (error) { console.log(error); });
		},
		izaberiStavku: function (stavka) {
			this.novaStavka = JSON.parse(JSON.stringify(stavka));
			this.submitovano = false;
		},
		izmeniStavku : function () {
			this.submitovano = true;
			if (document.getElementById('forma-izmeni-stavku').checkValidity() === true) {
				axios
				.put('stavkeCenovnika/izmeni', this.novaStavka, { headers: { Authorization: 'Bearer ' + this.token }} )
				.then(response => {
					toast("Uspešno izmenjena cena.");
					this.dobaviSve();
				})
				.catch(error => {
					console.log(error);
				});
			}
		},
		izaberiPopust: function (popust) {
			this.noviPopust = JSON.parse(JSON.stringify(popust));
			this.pocetak = this.datum2(popust.pocetak);
			this.kraj = this.datum2(popust.kraj);
			this.submitovano2 = false;
		},
		izmeniPopust : function () {
			this.okejDatumi = this.pocetak < this.kraj;
			this.submitovano2 = true;
			if (document.getElementById('forma-izmeni-popust').checkValidity() === true && this.okejDatumi) {
				this.noviPopust.pocetak = new Date(this.pocetak).getTime();
				this.noviPopust.kraj = new Date(this.kraj).getTime();
				axios
				.put('popusti/izmeni', this.noviPopust, { headers: { Authorization: 'Bearer ' + this.token }} )
				.then(response => {
					toast("Uspešno izmenjen popust.");
					this.dobaviSve();
				})
				.catch(error => {
					console.log(error);
				});
			} else {
				if (!(document.getElementById('forma-izmeni-popust').checkValidity() === true)) {
					this.okejDatumi = true;
				}
			}
		},
		dodajNulu: function (n) {
			if (n <= 9) {
				return "0" + n;
			}
			return n
		},
		datum: function (milisekunde) {
			var dat = new Date(milisekunde);
			return this.dodajNulu(dat.getDate()) + "." + this.dodajNulu(dat.getMonth() + 1) + "." + dat.getFullYear();
		},
		datum2: function (milisekunde) {
			var dat = new Date(milisekunde);
			return dat.getFullYear() + "-" + this.dodajNulu(dat.getMonth() + 1) + "-" + this.dodajNulu(dat.getDate());
		},
	},
	created() {
		this.token = localStorage.getItem("token");
		axios
        .get('cenovnici/ucitajCenovnik', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => {
    		this.cenovnik = response.data;
    		this.cenovnik.stavke.sort(function(a, b){return a.cena - b.cena});
    	})
        .catch(function (error) { console.log(error); });
		axios
        .get('popusti/ucitajSve', { headers: { Authorization: 'Bearer ' + this.token }} )
        .then(response => (this.popusti = response.data))
        .catch(function (error) { console.log(error); });
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