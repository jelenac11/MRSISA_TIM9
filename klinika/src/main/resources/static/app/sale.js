Vue.component("sale", {
	data : function() {
		return {
			ulogovan: {},
			sale: [],
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
						<router-link :to="{ path: 'sale'}" data-toggle="pill" class="nav-link active">Sale</router-link>
					</li>
					<li class="nav-item">
						<router-link :to="{ path: 'api/zahtjevi'}" data-toggle="pill" class="nav-link">Godišnji odmori</router-link>
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
		
		<div class="tab-content">
			<table class="table table-hover table-striped">
			  	<thead class="thead-light">
			    	<tr>
				      	<th scope="col" width="20%">Broj</th>
				      	<th scope="col" width="80%">Dodatne informacije</th>
			    	</tr>
			  	</thead>
			  	<tbody>
			  		<tr v-for="sala in sale" data-toggle="modal" data-target="#" v-on:click="">
				      	<td width="20%">{{ sala.broj }}</td>
				      	<td width="80%">{{ sala.info }}</td>
			    	</tr>
			  	</tbody>
			</table>
			<router-link :to="{ path: 'dodavanjeSale'}" class="btn btn-primary btn-block btn-lg my-2 p-2" id="dodavanjeSale">Dodaj novu salu</router-link>
		</div>
	</div>
	`
	,
	mounted() {
		axios
        .get('/api/getUserInfo')
        .then(response => (this.ulogovan = response.data))
        .catch(function (error) { console.log(error); });
		axios
        .get('/ucitajSale')
        .then(response => (this.sale = response.data))
        .catch(function (error) { console.log(error); });
	}
});