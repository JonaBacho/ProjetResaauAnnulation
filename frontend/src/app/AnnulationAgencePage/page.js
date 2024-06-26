"use client";
import React, { useContext, useState, useEffect } from "react";
import Link from "next/link";
import { UserContext } from "@/servicesAuth/authService";
import axios from 'axios';
import NavBar from "@/components/navbarAgency";
import Footer from "@/components/footer";
import Swal from "sweetalert2";



export default function AnnulationClientPage() {

    const [agencyTrips, setAgencyTrips] = useState([]);
    const [agency, setAgency] = useState(null);
    const {user} = useContext(UserContext);


    useEffect(() => {
        if (user && user.userId) {
            fetchAgencyData(user.userId).then(r => {
            });
        }
    }, [user]);


    const fetchAgencyData = async (userId) => {
        try {
            const response = await axios.get(`http://localhost:8000/api/getClient/${userId}`);
            setAgency(response.data[0]);
        } catch (error) {
            console.log('erreur lors du chargement des informations de l\'agence', error);
        }
    };


    useEffect(() => {
        if (agency) {
            console.log('Utilisateur disponible:', agency);
            fetchAgencyTrips(agency.id_utilisateur).then(r => {});
        }
    }, [agency]);


    const fetchAgencyTrips = async (idUser) => {
        try {
            const response = await axios.get(`http://localhost:8000/api/get/${idUser}`);
            console.log('Réponse API pour les voyages :', response.data);
            setAgencyTrips(response.data);
        } catch (error) {
            console.error('Error fetching trips:', error);
            await Swal.fire({
                icon: 'error',
                title: 'Erreur',
                text: 'Erreur lors de la récupération des voyages. Veuillez réessayer plus tard.',
            });
        }
    };



    if (!user)
    {
        return (
            <div>
                <p className="text-center text-3xl font-bold text-red-600 ">Vous n'êtes pas connecté</p>
                <Link className="text-xl mt-10" href="/login/LoginClient">Connectez vous pour continuer ... </Link>
            </div>
        );
    }
    else
    {
        return (
            <>
                <div className="flex flex-col min-h-screen">
                    <NavBar />
                    <main className="flex-grow">
                        <div className="container mx-auto p-4 mt-10">
                            <h1 className="text-3xl font-bold mb-10">Tous Nos Voyages Publiés</h1>
                            <div className="overflow-x-auto mb-10">
                                <table className="min-w-full bg-white border border-gray-200 rounded-lg shadow-md">
                                    <thead>
                                    <tr className="bg-blue-100">
                                        <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">#</th>
                                        <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">ID du voyage</th>
                                        <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">Date de Depart</th>
                                        <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">Nombre places réservables</th>
                                        <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">Nombre de réservations</th>
                                        <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">Action</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {agencyTrips.map((trip, index) => (
                                        <tr key={trip.id} className={index % 2 === 1 ? 'bg-blue-50' : 'bg-white'}>
                                            <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">{index + 1}</td>
                                            <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">{trip.id_piece}</td>
                                            <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">{trip.nom_piece}</td>
                                            <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">{trip.prix_piece}</td>
                                            <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">{/*{trip.description_piece}*/} {index + 4}</td>
                                            <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">
                                                <Link href={`/AnnulationAgencePage/${trip.id_piece}`}>
                                                    <button
                                                        className="bg-red-400 hover:bg-red-600 text-white py-1 px-3 rounded-md"
                                                    >
                                                        Annuler
                                                    </button>
                                                </Link>
                                            </td>
                                        </tr>
                                    ))}
                                    </tbody>
                                </table>
                            </div>
                        </div>
                    </main>
                    <Footer />
                </div>
            </>
        );
    }
}
