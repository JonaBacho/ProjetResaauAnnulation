"use client";
import NavBar from "@/components/navbar";
import Footer from "@/components/footer";
import React, {useContext, useEffect, useState} from "react";
import {UserContext} from "@/servicesAuth/authService";
import axios from "axios";
import Swallow from "sweetalert2";
import Swal from "sweetalert2";


export default function Coupons()
{

    const [utilisateur, setUser] = useState(null);
    const [coupons, setCoupons] = useState([]);

    const { user} = useContext(UserContext);


    useEffect(() => {
        if (user && user.userId) {
            fetchUserData(user.userId).then(r => {
            });
        }
    }, [user]);


    const fetchUserData = async (userId) => {
        try {
            const response = await axios.get(`http://localhost:8000/api/getClient/${userId}`);
            setUser(response.data[0]);
        } catch (error) {
            console.log('erreur lors du chargement des informations de l\'utilisateur', error);
        }
    };



    useEffect(() => {
        if (utilisateur) {
            fetchCouponsUser(utilisateur.id_utilisateur).then(r => {});
        }
    }, [utilisateur]);

    const fetchCouponsUser = async (idUser)=>

    {
        try {
            const response = await axios.get(`http://localhost:8000/api/getHouse/${idUser}`);
            setCoupons(response.data);
        } catch (error) {
            await Swallow.fire({
                icon: 'error',
                title: 'Erreur de chargements',
                text: 'impossible d\'afficher les coupons, réessayer plutard',
            });
            console.error('Error fetching trips:', error);
        }
    }




    const showCouponDetails = (coupon) => {
        Swallow.fire({
            title: 'Détails du coupon',
            html: `
                <p><strong>ID du coupon:</strong> ${coupon.id_maison}</p>
                <p><strong>Nom de l'agence </strong> ${coupon.lieu}</p>
                <p><strong>Valeur:</strong> ${coupon.prix_maison} points</p>
                <p><strong>Description du coupon</strong> ${coupon.description_maison}</p>
            `,
            icon: 'info',
            confirmButtonText: 'OK'
        }).then(r => {});
    }

        return (
            <div className="flex flex-col min-h-screen">
                <NavBar />
                <main className="flex overflow-auto mb-5">
                    <div className="ml-5 mt-8 mr-5 w-2/3 h-sreen">
                        <div className="mt-4 mb-5 flex">
                            <div className="container mx-auto p-4 mt-2">
                                <h1 className="text-3xl font-bold mb-10 text-center">Tous Mes coupons</h1>
                                {coupons.length > 0 ? (
                                    <div className="overflow-x-auto max-h-screen mb-10">
                                        <table className="min-w-full bg-white border border-gray-200 rounded-lg shadow-md">
                                            <thead>
                                            <tr className="bg-blue-100">
                                                <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">#</th>
                                                <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">ID du coupon</th>
                                                <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">Nom de l'agence</th>
                                                <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">Valeur</th>
                                                <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">Action</th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            {coupons.map((piece, index) => (
                                                <tr key={piece.id} className={index % 2 === 1 ? 'bg-blue-50' : 'bg-white'}>
                                                    <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">{index + 1}</td>
                                                    <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">{piece.id_maison}</td>
                                                    <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">{piece.lieu}</td>
                                                    <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">{piece.prix_maison}</td>
                                                    <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">
                                                        <button
                                                            onClick={() => showCouponDetails(piece)}
                                                            className="bg-blue-400 hover:bg-blue-600 text-white py-1 px-3 rounded-md"
                                                        >
                                                            Afficher en détails
                                                        </button>
                                                    </td>
                                                </tr>
                                            ))}
                                            </tbody>
                                        </table>
                                    </div>
                                ) : (
                                    <p className="text-center text-gray-600 ">Aucun coupon trouvé.</p>
                                )}
                            </div>
                        </div>
                    </div>
                    <div className="w-1/3 ml-2 mt-10 mb-5 mr-10">
                        <h2 className=" mt-5 font-bold text-center text-xl">Politique de coupons </h2>
                        <p className=" mt-10 text-justify font-bold text-gray-600">Un coupon de voyage est un document électronique ou physique
                            qui est émis lorsqu'un voyage est annulé. Il contient des détails essentiels sur la réservation
                            annulée et peut servir de preuve pour toute transaction future liée à cette annulation.</p>
                        <p className=" mt-4 text-justify font-bold  text-gray-600">Cette politique décrit les conditions d'utilisation des coupons de voyage délivrés par notre agence de voyage.
                            Les coupons peuvent être émis en cas d'annulation de voyage par l'agence ou par le client.</p>
                        <p className=" mt-4 text-justify font-bold  text-gray-600">Annulation par l'Agence : Si notre agence de voyage annule un voyage pour des raisons telles que des circonstances imprévues ou
                            des contraintes opérationnelles, un coupon de voyage équivalent au montant total payé sera émis au nom du passager principal.</p>
                        <p className=" mt-4 text-justify font-bold  text-gray-600">En cas d'annulation de voyage par le client conformément aux conditions générales, un coupon de voyage peut être émis dependant
                        du jour de l'annulation, du nombre d'annulation pour ledit voyage</p>
                    </div>
                </main>
                <Footer />
            </div>
        );
    };
