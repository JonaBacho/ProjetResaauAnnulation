"use client";
import {useContext, useState} from "react";
import Link from "next/link";
import {UserContext} from "@/servicesAuth/authService";



export default function LoginAdmin()
{

    const [loginAgencyData, setLoginAgency] = useState({});
    const {loginAgency} = useContext(UserContext);

    const handleSubmit = async (e) => {
        e.preventDefault();
        await loginAgency(loginAgencyData);
    };


    const handleChange = (e) => {
        e.preventDefault();
        const {name, value} = e.target;
        setLoginAgency({
            ...loginAgencyData,
            [name]: value,
        });
    };



    return (
        <div className="flex justify-center items-center h-screen bg-tertiary">
            <div className="bg-white h-[400px] w-[400px] border-2 border-blue-600 rounded-2xl">
                <div className="flex justify-center">
                    <p className="text-bold text-2xl mt-10">Connectez vous</p>
                </div>
                <form className="mt-10" onSubmit={handleSubmit}>
                    <label className="ml-10"
                           htmlFor="username">Email:
                    </label>
                    <input
                        className="ml-2 px-2 text-gray-600 mb-5 mt-2 border border-gray-300 rounded-lg h-[35px] w-[250px]"
                        type="text"
                        name="email"
                        onChange={handleChange}
                        value = {loginAgencyData.email || ""}
                        placeholder="email"
                        required/>
                    <br/>
                    <label className="ml-10 mr-5 "
                           htmlFor="password">Mot de passe :
                    </label>
                    <input
                        className="ml-2 mb-10 px-2 text-gray-600 mt-2 border border-gray-300 rounded-lg h-[35px] w-[200px]"
                        type="password"
                        name="password"
                        onChange={handleChange}
                        value = {loginAgencyData.password || ""}
                        placeholder="Mot de passe"
                        required
                    />
                    <div className="flex justify-center">
                        <button
                            type ="submit"
                            className="bg-blue-600 text-white w-[140px] h-[50px]
                            rounded-2xl mt-10
                            hover:bg-indigo-700 focus:outline-none
                            focus:ring-2 focus:ring-offset-2
                            focus:ring-indigo-500">
                            Se connecter
                        </button>
                    </div>
                    <div className="ml-2 mt-5">
                        <Link  className="text-sm text-blue-600 hover:text-indigo-500" href="/login/LoginClient"> Etes vous un passger ? </Link>
                    </div>
                </form>
            </div>
        </div>
    );
}