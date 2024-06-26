"use client";
import NavBar from "@/components/navbar";
import Footer from "@/components/footer";
import axios from "axios";
import { useEffect, useState } from "react";
import Image from 'next/image';
import PhotoBus from "../../../../public/photoVoyage.avif";
import Swal from "sweetalert2";



export default function AnnulationAgencyPage({ params }) {


    const [agencyTrip, setAgencyTrip] = useState([]);
    const tripId = params.idVoyage;
    console.log(tripId);

    useEffect(() => {
        if (tripId) {
            fetchUserTrips(tripId).then(r => {});
        }
    }, [tripId]);

    const fetchUserTrips = async (Id) => {
        try {
            const response = await axios.get(`http://localhost:8000/api/piece/${Id}`);
            setAgencyTrip(response.data);
        } catch (error) {
            console.error('Error fetching trips:', error);
        }
    };


    const handleDelete = async (piece) => {
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
                idVoyage: piece.id_piece,
                nom_piece: piece.nom_piece,
                prix_piece: piece.prix_piece,
                description_piece: piece.description_piece,
                etat: piece.etat
            };

            try {
                const response = await axios.post('http://localhost:8000/api/piece/', dataToSend);
                await Swal.fire('Annulation réussie', 'Les raisons et les informations du voyage ont été envoyées avec succès.', 'success');
                //fetchUserTrips(tripId);
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
                        <div className="ml-2 mt-8 mr-5 w-2/3 rounded-lg">
                            <div className="flex">
                                    <div className='mt-1 h-3/4 w-full '>
                                        <Image className="w-full h-[400px] px-5 py-3 mt-2" src={PhotoBus}
                                               alt="voyage"/>
                                    </div>
                            </div>
                                <div className='mt-2 h-1/4 w-full mb-5'>
                                    <p className="mt-5 ml-10 text-bold font-bold text-2xl text-red-400"> Politique d'annulation </p>
                                    <p className="mt-5 5 ml-5 mb-1 font-bold  mr-2"> Pour compenser l'annulation de  voyage, nous offrons les options suivantes : </p>
                                       <p className="ml-4 mt-4 mr-2 text-justify"> * Coupon de Valeur Égale : Les passagers affectés recevront un coupon d'une valeur équivalente au montant de leur réservation pour le voyage annulé.
                                       Ce coupon peut être utilisé pour réserver un autre voyage avec notre agence dans un délai spécifié.
                                       </p>
                                       <p className= "ml-4  mr-2 text-justify"> * Coupon avec Surplus : Dans certains cas, nous offrons un coupon d'une valeur supérieure à celle du montant que vous avez payé pour le voyage annulé. Ce coupon permettra aux clients
                                           de l'agence de bénéficier d'un avantage supplémentaire lors de leurs prochaines réservations avec nous
                                       </p>
                                </div>
                        </div>

                        {/*deuxieme colonne */}
                        <div className="mt-8 ml-5 mr-4 w-1/3 ">
                            <table className="min-w-full h-full bg-white border border-blue-300 rounded-xl shadow-md ">
                                <thead>
                                <tr className="bg-blue-400">
                                    <th className="border-b border-gray-300 px-6 py-3 text-center text-xl font-bold text-gray-900 uppercase tracking-wider">Détails du voyage
                                    </th>
                                </tr>
                                </thead>
                                <tbody>
                                {agencyTrip.map((trip, index) => (
                                    <tr key={trip.id_piece} className = 'bg-blue-50'>
                                        <td className="border-b border-gray-300 px-6 py-4">
                                            <div className="flex flex-col">
                                                <div className="mb-2"><strong>Numero du voyage:</strong> {trip.id_piece}</div>
                                                <div className="mb-2"><strong>Nom:</strong> {trip.nom_piece}</div>
                                                <div className="mb-2"><strong>Prix du voyage :</strong> {trip.prix_piece}</div>
                                                <div className="mb-2">
                                                    <strong>Description du voyage:</strong> {trip.description_piece}</div>
                                                <div><strong>État:</strong> {trip.etat}</div>
                                                <div className="mt-10 flex items-center justify-center">
                                                    <button
                                                        onClick={() => handleDelete(trip)}
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
                            {/*   <div className=" flex justify-center items-center h-1/2 mr-5 mt-6">
                                <table
                                    className="rounded-lg shadow-md min-w-full h-full bg-white border border-blue-300">
                                    <thead className="bg-blue-100">
                                    <tr>
                                        <th className="border-b border-gray-300 px-6 py-3 text-lg font-bold text-gray-600 uppercase tracking-wider text-center"
                                            colSpan="2">
                                            Détails du voyage
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr className={`bg-blue-50`}>
                                        <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">ID du
                                            voyage
                                        </td>
                                        <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">{agencyTrip[0].id_piece}</td>
                                    </tr>
                                    <tr className={`bg-white`}>
                                        <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">ID de l'agence
                                        </td>
                                        <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">{agencyTrip[0].nom_piece}</td>
                                    </tr>
                                    <tr className={`bg-blue-50`}>
                                        <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">Nombre de
                                            places réservés
                                        </td>
                                        <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">{agencyTrip[0].prix_piece}</td>
                                    </tr>
                                    <tr className={`bg-blue-50`}>
                                        <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap"> Lieu de depart
                                        </td>
                                        <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">{agencyTrip[0].prix_piece}</td>
                                    </tr>
                                    {/* Ajoutez ici les autres propriétés de l'objet agencyTrip
                                    </tbody>
                                </table>
                            </div>
                            <div className="mt-10 flex items-center justify-center">
                                <button
                                    onClick={() => handleDelete(trip)}
                                    className="bg-red-400 border hover:bg-red-600 text-white w-[100px] h-[50px] rounded-2xl mt-10 duration-300 transition-all">
                                    Annuler
                                </button>
                            </div>
                            */}
                        </div>
                    </div>
                </main>
                <Footer/>
            </div>
        </>
    );
}
