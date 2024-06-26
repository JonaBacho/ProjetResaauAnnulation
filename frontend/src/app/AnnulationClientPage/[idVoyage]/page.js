"use client";
import NavBar from "@/components/navbar";
import Footer from "@/components/footer";
import axios from "axios";
import { useEffect, useState } from "react";
import Image from 'next/image';

import PhotoBus from "../../../../public/voyageBus.jpg";
import Swal from "sweetalert2";



export default function AnnulationClientPage({ params }) {
    const [clientTrips, setClientTrips] = useState([]);
    const tripId = params.idVoyage;

//console.log(tripId)

    useEffect(() => {
        if (tripId) {
            fetchUserTrips(tripId).then(r => {});
        }
    }, [tripId]);

    const fetchUserTrips = async (Id) => {
        try {
            const response = await axios.get(`http://localhost:8000/api/piece/${Id}`);
            setClientTrips(response.data);
        } catch (error) {
            console.error('Error fetching trips:', error);
        }
    };

    const handleDelete = async (trip) => {
        const result = await Swal.fire({
            title: 'Causes de l\'annulation',
            input: 'textarea',
            inputLabel: 'Quelles peuvent être les raisons de l\'annulation de votre voyage ?',
            inputPlaceholder: 'Saisissez les détails ici...',
            inputAttributes: {
                'aria-label': 'Saisissez les détails ici',
            },
            showCancelButton: true,
            confirmButtonText: 'Envoyer',
            cancelButtonText: 'Annuler',
            showLoaderOnConfirm: true,
            preConfirm: (value) => {
                if (!value) {
                    Swal.showValidationMessage('Veuillez saisir les causes de l\'annulation');
                }
                return value;
            },
        });

        if (result.isConfirmed && result.value) {
            const text = result.value;
            const dataToSend = {
                causesAnnulation: text,
                idVoyage: trip.id_piece,
                nom_piece: trip.nom_piece,
                prix_piece: trip.prix_piece,
                description_piece: trip.description_piece,
                etat: trip.etat
            };

            try {
                const response = await axios.post('http://localhost:8000/api/piece/', dataToSend);
                await Swal.fire('Annulation réussie', 'Les raisons et les informations du voyage ont été envoyées avec succès.', 'success');
                if (typeof window !== 'undefined') {
                    window.location.href = '/AnnulationClientPage';
                }

            } catch (error) {
                await Swal.fire('Erreur', `Une erreur est survenue : ${error.message}`, 'error');
            }
        }
    };

    return (
        <>
            <div className="flex flex-col min-h-screen">
                <NavBar/>
                <main className="flex-grow overflow-y-auto mb-5 ">
                    <div className="mt-4 flex min-h-[calc(100vh-100px)]">
                        {/* Première colonne */}
                        <div className="bg-tertiary ml-5 mt-8 mr-5 w-2/3">
                            <div className="flex">
                                <div className="w-3/4">
                                    <div className='mt-1 h-3/4 w-full bg-tertiary'>
                                        <Image className="w-[550px] h-[400px] px-7 py-3 mt-5 ml-5" src={PhotoBus}
                                               alt="voyage"/>
                                    </div>
                                </div>
                                <div className="mt-4 mr-3 w-1/3">
                                    <p className="text-2xl mt-4 font-bold mb-4">Description du voyage</p>
                                    <p className="text-sm leading-relaxed text-gray-600 text-justify mr-5">
                                        Partez à la découverte d'une destination envoûtante où chaque coin de rue
                                        raconte une histoire. Ce voyage vous transporte à travers des paysages
                                        spectaculaires, des villes historiques et des cultures fascinantes. De l'aube au
                                        crépuscule, explorez des sites emblématiques et des trésors cachés, tout en
                                        savourant une cuisine locale riche en saveurs et en traditions.
                                    </p>
                                </div>
                            </div>
                            <div className='mt-2 h-1/4 w-full  bg-tertiary-600 mb-5'>
                                <p className="mt-5 ml-10 text-bold font-bold text-2xl text-red-400"> Politique d'annulation </p>
                                <p className="mt-5 5 ml-5 mb-1 font-bold text-justify mr-2"> Les clients peuvent annuler leur voyage en utilisant notre portail en
                                    ligne ou en contactant notre service client.
                                    Les annulations doivent être effectuées avant la date de départ prévue.
                                    Les frais d'annulation peuvent s'appliquer en fonction du délai d'annulation.
                                    En fonction de la période, un taux de remboursement prenant en compte des facteurs budgetaires,
                                    internes et externes, sera attribué par apport au prix de votre billet, et un coupon vous sera remis</p>
                            </div>
                        </div>
                        <div className="bg-tertiary mt-8 ml-5 mr-5 w-1/4">
                            <table className="min-w-full h-full bg-white border border-gray-200 rounded-lg shadow-md ">
                                <thead>
                                <tr className="bg-blue-100">
                                    <th className="border-b border-gray-300 px-6 py-3 text-center text-xl font-bold text-gray-600 uppercase tracking-wider">Détails du voyage
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                {clientTrips.map((piece, index) => (
                                    <tr key={piece.id_piece} className = 'bg-blue-50'>
                                        <td className="border-b border-gray-300 px-6 py-4">
                                            <div className="flex flex-col">
                                                <div className="mb-2"><strong>Numero du voyage:</strong> {piece.id_piece}</div>
                                                <div className="mb-2"><strong>Nom:</strong> {piece.nom_piece}</div>
                                                <div className="mb-2"><strong>Prix du voyage :</strong> {piece.prix_piece}</div>
                                                <div className="mb-2">
                                                    <strong>Description du voyage:</strong> {piece.description_piece}</div>
                                                <div><strong>État:</strong> {piece.etat}</div>
                                                <div className="mt-10 flex items-center justify-center">
                                                        <button
                                                            onClick={() => handleDelete(piece)}
                                                            className="bg-red-400 hover:bg-red-600 text-white py-1 px-3 rounded-md">
                                                            Annuler
                                                        </button>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>
                    </div>
                </main>
               <Footer/>
            </div>
        </>
    );
}
