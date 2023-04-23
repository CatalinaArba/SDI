import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import React from 'react'
import { PetsShowAll } from './components/pets/PetsShowAll'
import { AppHome } from './components/AppHome'
import { AppMenu } from './components/AppManu'
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import { PetDetails } from './components/pets/PetDetails'
import { PetDelete } from './components/pets/PetDelete'
import { PetAdd } from './components/pets/PetAdd'
import { PetPriceStatistics } from './components/pets/PetPriceStatistics'
import { PetUpdate } from './components/pets/PetUpdate'
import { AdoptionShowAll } from './components/adoptions/AdoptionShowAll'
import { AdoptionCustomerShowAll } from './components/adoptionCustomers/AdoptionCustomerShowAll'
import { AdoptionDetails } from './components/adoptions/AdoptionDetails'
import { AdoptionCustomerDetails } from './components/adoptionCustomers/AdoptionCustomerDetails'
import { AdoptionAdd } from './components/adoptions/AdoptionAdd'
import { AdoptionCustomerAdd } from './components/adoptionCustomers/AdoptionCustomerAdd'


function App() {
  return (
		<React.Fragment>
			<Router>
				<AppMenu />

				<Routes>
					<Route path="/" element={<AppHome />} />
					<Route path="/pets" element={<PetsShowAll />} />
					<Route path="/pets/:id/details" element={<PetDetails />} />
					<Route path="/pets/:id/delete" element={<PetDelete />} />
					<Route path="/pets/:id/edit" element={<PetUpdate />} />
					<Route path="/pets/add" element={<PetAdd />} />
					<Route path="/pets/price" element={<PetPriceStatistics />} />
					<Route path="/adoptions" element={<AdoptionShowAll />} />
					<Route path="/adoptionCustomer" element={<AdoptionCustomerShowAll />} />
					<Route path="/adoptions/:id/details" element={<AdoptionDetails />} />
					<Route path="/adoptionCustomer/:id/details" element={<AdoptionCustomerDetails />} />
					<Route path="/adoptions/add" element={<AdoptionAdd />} />
					<Route path="/adoptionCustomer/add" element={<AdoptionCustomerAdd />} />
				</Routes>
			</Router>
		</React.Fragment>
	);
}

export default App
