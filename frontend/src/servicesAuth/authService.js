"use client";
import { createContext, useState, useEffect } from 'react';
import axios from 'axios';
import Swal from "sweetalert2";

export const UserContext = createContext(null);

export const UserProvider = ({ children }) => {
    const [user, setUser] = useState(null);

    useEffect(() => {
        if (typeof window !== 'undefined') {
            const idUser = localStorage.getItem('idUser');
            if (idUser) {
                setUser({ idUser });
            }
        }
    }, []);

    const login = async (loginData) => {
        try {
            const response = await axios.post('http://localhost:9000/api/login/user', loginData);
            const data = response.data;
            localStorage.setItem('idUser', data.idUser);
            setUser({ idUser: data.idUser});
            await Swal.fire({
                icon: 'success',
                title: 'Authentification réussie',
                text: 'Vous vous êtes authentifié avec succès!',
            });
            if (typeof window !== 'undefined') {
                window.location.href = '/AnnulationClientPage';
            }
        } catch (error) {
            await Swal.fire({
                icon: 'error',
                title: 'Authentification échouée',
                text: 'Mot de passe ou email incorrect. Réessayez.',
            });
            console.error('Login failed', error);
        }
    };

    const loginAgency = async (loginAgencyData) => {
        try {
            const response = await axios.post('http://localhost:9000/api/login/agence', loginAgencyData);
            const data = response.data;
            localStorage.setItem('idUser', data.idUser);
            setUser({ idUser: data.idUser });
            await Swal.fire({
                icon: 'success',
                title: 'Authentification réussie',
                text: 'Vous vous êtes authentifié avec succès!',
            });
            if (typeof window !== 'undefined') {
                window.location.href = '/AnnulationAgencePage';
            }
        } catch (error) {
            await Swal.fire({
                icon: 'error',
                title: 'Authentification échouée',
                text: 'Mot de passe ou email incorrect. Réessayez.',
            });
            console.error('Login failed', error);
        }
    };


    const logout = () => {
        localStorage.removeItem('idUser');
        setUser(null);
        if (typeof window !== 'undefined') {
            window.location.href = '/';
        }
    };


    return (
        <UserContext.Provider value={{ user, login, loginAgency, logout }}>
            {children}
        </UserContext.Provider>
    );
};