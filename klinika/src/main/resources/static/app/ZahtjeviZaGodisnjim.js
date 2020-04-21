Vue.component("zahtjevGodisnji",{
	data : function(){
		return {
            zahtjevi : null,
            indeks:null,
            ulogovan: {},
            submitovano : false,
		}
	},
	
    template: `
<div>
    <nav class="navbar navbar-expand-md navbar-dark bg-dark">
		   <div class="navbar-collapse collapse w-100 order-1 order-md-0 dual-collapse2">
		       <ul class="nav nav-pills" role="tablist">
		        <li class="nav-item">
					<router-link :to="{ path: 'lekari'}" data-toggle="pill" class="nav-link">Lekari</router-link>
				</li>
				<li class="nav-item">
					<router-link :to="{ path: 'sale'}" data-toggle="pill" class="nav-link">Sale</router-link>
				</li>
				<li class="nav-item">
					<router-link :to="{ path: 'zahtjevi'}" data-toggle="pill" class="nav-link active">Zahtevi za odsustvom</router-link>
				</li>
			</ul>
		   </div>
		   
		   <div class="navbar-collapse collapse w-100 order-1 dual-collapse2">
		        <ul class="navbar-nav ml-auto">
		            <li class="nav-item dropdown">
		                <a class="navbar-brand dropdown-toggle" href="#" data-toggle="dropdown">
						    <img src="profilepic.png" width="30" height="30" class="d-inline-block align-top" alt="">
						    {{ this.ulogovan.ime + " " + this.ulogovan.prezime }}
					  	</a>
					  	<div class="dropdown-menu">
					  		<a class="dropdown-item" data-toggle="modal" data-target="#profilModal" href='#'>Pregled profila</a>
				        	<a class="dropdown-item" v-on:click="" href="#">Odjavi se</a>
				        </div>
		            </li>
		        </ul>
		    </div>
    </nav>
	
		<div class="modal fade" id="profilModal" tabindex="-1" role="dialog">
			<div class="modal-dialog" role="document">
		    	<div class="modal-content">
					<div class="modal-header">
		        		<h5 class="modal-title" id="exampleModalLabel">Pregled profila</h5>
		        		<button type="button" class="close" data-dismiss="modal">
		          			<span>&times;</span>
		        		</button>
		      		</div>
		      		<div class="modal-body">
		        		<ul class="list-group">
						  	<li class="list-group-item">
						  		<div class="d-flex w-20 justify-content-between">
							  		<h6>Email adresa:</h6>
							  		<p class="mb-0">{{ this.ulogovan.email }}</p>
							  	</div>
						  	</li>
						  	<li class="list-group-item">
						  		<div class="d-flex w-20 justify-content-between">
							  		<h6>Ime:</h6>
							  		<p class="mb-0">{{ this.ulogovan.ime }}</p>
							  	</div>
						  	</li>
						  	<li class="list-group-item">
						  		<div class="d-flex w-20 justify-content-between">
						  			<h6>Prezime:</h6>
						  			<p class="mb-0">{{ this.ulogovan.prezime }}</p>
						  		</div>
						  	</li>
						  	<li class="list-group-item">
						  		<div class="d-flex w-20 justify-content-between">
							  		<h6>Adresa:</h6>
							  		<p class="mb-0">{{ this.ulogovan.adresa }}</p>
							  	</div>
						  	</li>
						  	<li class="list-group-item">
						  		<div class="d-flex w-20 justify-content-between">
						  			<h6>Grad:</h6>
						  			<p class="mb-0">{{ this.ulogovan.grad }}, {{this.ulogovan.drzava}}</p>
						  		</div>
						  	</li>
						  	<li class="list-group-item">
						  		<div class="d-flex w-20 justify-content-between">
							  		<h6>Broj osiguranika:</h6>
							  		<p class="mb-0">{{ this.ulogovan.brOsiguranika }}</p>
							  	</div>
						  	</li>
						</ul>
		      		</div>
		      		<div class="modal-footer">
		        		<button type="button" class="btn btn-secondary mr-auto" data-dismiss="modal">Nazad</button>
		        		<router-link :to="{ path: 'izmenaProfila'}" class="btn btn-primary" data-dismiss="modal">Izmena podataka</router-link>
		      		</div>
		    	</div>
			</div>
		</div>
        <table class="table table-hover table-striped">
            <thead class="thead-light">
                <tr>
                    <th scope="col">Ime</th>
                    <th scope="col">Prezime</th>
                    <th scope="col">Početak odsustva</th>
                    <th scope="col">Kraj odsustva</th>
                    <th scope="col" colspan="2">Akcija</th>
                </tr>

            </thead>
            <tbody>
                <tr v-for="(zahtjev,indeks) in zahtjevi">
                    <td>{{zahtjev.podnosilac.ime}}</td>
                    <td>{{zahtjev.podnosilac.prezime}}</td>
                    <td>{{urediDatum(zahtjev.pocetak)}}</td>
                    <td>{{urediDatum(zahtjev.kraj)}}</td>
                    <td><button type="button" class="btn btn-success" v-on:click="prihvatanjeZahtjeva(indeks)">Prihvati zahtev</button></td>
                    <td><button type="button" class="btn btn-danger" data-target="#obrazlozenjeModal" data-toggle="modal" v-on:click="zabiljeziIndeks(indeks)">Odbij zahtev</button></td>
                </tr>
            </tbody>
        </table>
        <div class="modal fade" id="obrazlozenjeModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
		<form class="needs-validation mb-4" id="forma-obrazlozenje-odbijanja" v-bind:class="{ 'was-validated': submitovano }" novalidate @submit.prevent="odbijanjeZahtjeva">
	    	<div class="modal-content">
				<div class="modal-header">
	        		<h5 class="modal-title" id="exampleModalLabel">Obrazlozenje odbijanja zahteva</h5>
	        		<button type="button" class="close" data-dismiss="modal">
	          			<span>&times;</span>
	        		</button>
	      		</div>
	      		<div class="modal-body">
                  	<input id="obrazlozenje" type="text" class="form-control" placeholder="Unesite obrazlozenje odbijanja zahteva" required>
	      			<div class="invalid-feedback" id="dodavanjeInvalid">Unesite razlog odbijanja zahteva za odsustvom.</div>
	      		</div>
	      		<div class="modal-footer">
	        		<button type="button" class="btn btn-secondary mr-auto" data-dismiss="modal">Nazad</button>
	        		<button class="btn btn-primary" type="submit">Odbij zahtev</button>
	      		</div>
	    	</div>
	    </form>
		</div>
		</div>
    </div>
	`
		,
	mounted (){
        this.dobaviZahtjeve()
        axios
        .get('/api/getUserInfo')
        .then(response => (this.ulogovan = response.data))
        .catch(function (error) { console.log(error); });
	},
	methods:
    {
		prihvatanjeZahtjeva: function(indeks){
			this.zahtjevi[indeks].odgovoreno=true
            this.zahtjevi[indeks].odobreno=true
            this.zahtjevi[indeks].obrazlozenje=""
            this.updateZahtjev(indeks)
        },
        zabiljeziIndeks: function(indeks){
            this.indeks=indeks;
            $("#obrazlozenje").val("");
            this.submitovano=false;
        },
		odbijanjeZahtjeva: function(){
			this.submitovano = true;
			if (document.getElementById('forma-obrazlozenje-odbijanja').checkValidity() === true){
				this.zahtjevi[this.indeks].odgovoreno=true
	            this.zahtjevi[this.indeks].odobreno=false
	            this.zahtjevi[this.indeks].obrazlozenje=$("#obrazlozenje").val()
	            jQuery.noConflict();
				$('#obrazlozenjeModal').modal('hide');
	            this.updateZahtjev(this.indeks)
	            this.submitovano=false;
			}
        },
        updateZahtjev: function(indeks){
            axios.put('/odsustva/updateOdsustvo',this.zahtjevi[indeks]).then(response=>{this.dobaviZahtjeve()});
        },
        dobaviZahtjeve: function(){
            axios.get('/odsustva/ucitajSvaNeodgovorenaOdsustva').then(response => 
                {
                    this.zahtjevi =response.data;
                })
                .catch(function (error) { console.log(error); });
        },
        urediDatum:function(datum){
            var date = new Date(datum);
            datum=date.toLocaleDateString('en-GB', {
            day: 'numeric', month: 'short', year: 'numeric'
            }).replace(/ /g, '-');
            return datum
        },
	}
});