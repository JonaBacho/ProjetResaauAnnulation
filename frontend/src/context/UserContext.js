"use client";
import { createContext, useState, useEffect } from 'react';
import axios from 'axios';
import Swallow from "sweetalert2";

export const UserContext = createContext(null);

export const UserProvider = ({ children }) => {


    const [user, setUser] = useState(null);

    useEffect(() => {
        if (typeof window !== 'undefined') {
            const token = localStorage.getItem('token');
            if (token) {
                setUser({ token });
            }
        }
    }, []);


    const login = async (loginData) => {
        try {
            const response = await axios.post('http://localhost:8000/api/login', loginData);
            const data = response.data;

            localStorage.setItem('token', data.token);
            setUser({ token: data.token });
            await Swallow.fire({
                icon: 'success',
                title: 'Authentification réussie',
                text: 'Vous vous etes authententifé avec succès!',
            });
            if (typeof window !== 'undefined') {
                window.location.href = '/AnnulationClientPage';
            }
        } catch (error) {
            await Swallow.fire({
                icon: 'error',
                title: 'Authentification echouée',
                text: 'Mots de passe ou email incorrects. Reessayez.',
            });
            console.error('Login failed', error);
        }
    };

    const loginAdmin = async (loginAdminData) => {
        try {
            const response = await axios.post('http://localhost:8000/api/login', loginAdminData);
            const data = response.data;

            localStorage.setItem('token', data.token);
            setUser({ token: data.token });
            await Swallow.fire({
                icon: 'success',
                title: 'Authentification réussie',
                text: 'Vous vous etes authententifé avec succès!',
            });
            if (typeof window !== 'undefined') {
                window.location.href = '/AnnulationAgencePage';
            }
        } catch (error) {
            await Swallow.fire({
                icon: 'error',
                title: 'Authentification echouée',
                text: 'Mots de passe ou email incorrects. Reessayez.',
            });
            console.error('Login failed', error);
        }
    };


    const logout = () => {
        localStorage.removeItem('token');
        setUser(null);
        if (typeof window !== 'undefined') {
            window.location.href = '/';
        }
    };

    const fetchUserData = async () => {
        try {
            const idUser = localStorage.getItem('token');
            if (token) {
                const response = await axios.get('http://localhost:8000/api/user', {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                });
                setUser(response.data);
                console.log(utilisateur);
            } else {
                console.error('No token found');
            }
        } catch (error) {
            console.error('Failed to fetch user data:', error);
        }
    };

    return (
        <UserContext.Provider value={{ user, login, loginAdmin, logout, fetchUserData }}>
            {children}
        </UserContext.Provider>
    );
};


