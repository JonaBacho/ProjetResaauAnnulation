
/*export default function (){


    const [pieces, setPieces] = React.useState ( [
        {
            id_piece: 1,
            nom_piece: 'Voyage à Paris',
            prix_piece: 1200,
            description_piece: 'Découvrez les merveilles de la ville lumière',
            etat: 'Réservé'
        },
        {
            id_piece: 2,
            nom_piece: 'Escapade à Rome',
            prix_piece: 1500,
            description_piece: 'Plongez dans l\'histoire et la culture italienne',
            etat: 'Réservé'
        },
        {
            "id_piece": 3,
            "nom_piece": "Découverte de Londres",
            "prix_piece": 1800,
            "description_piece": "Explorez la ville aux mille facettes",
            "etat": "Réservé"
        },
        {
            "id_piece": 4,
            "nom_piece": "Week-end à New York",
            "prix_piece": 2200,
            "description_piece": "Vivez le rythme effréné de la Grosse Pomme",
            "etat": "Réservé"
        },
        {
            "id_piece": 5,
            "nom_piece": "Randonnée en Suisse",
            "prix_piece": 1700,
            "description_piece": "Contemplez les paysages époustouflants des Alpes",
            "etat": "Réservé"
        },
        {
            "id_piece": 6,
            "nom_piece": "Séjour à Venise",
            "prix_piece": 2000,
            "description_piece": "Laissez-vous transporter par la magie de la Sérénissime",
            "etat": "Réservé"
        },
        {
            "id_piece": 7,
            "nom_piece": "Visite de Barcelone",
            "prix_piece": 1600,
            "description_piece": "Découvrez l'architecture unique de la Catalogne",
            "etat": "Réservé"
        }
        // Add more pieces as needed
    ]);

    const [canceledPieces, setCanceledPieces] = useState([]);
    const [showCanceledPieces, setShowCanceledPieces] = useState(false);

    const handleCancel = (id) => {
        // Mettre à jour l'état de la réservation correspondante
        const updatedPieces = pieces.map((piece) => {
            if (piece.id_piece === id) {
                return { ...piece, etat: 'Annulé' };
            }
            return piece;
        });
        setPieces(updatedPieces);
        // Ajout de la réservation annulée au tableau des réservations annulées
        const canceledPiece = updatedPieces.find((piece) => piece.id_piece === id);
        setCanceledPieces([...canceledPieces, canceledPiece]);

        console.log(`Annulation de la réservation avec l'ID ${id}`);
    };

    const handleShowCanceledPieces = () => {
        setShowCanceledPieces(!showCanceledPieces);
    };



    return (
        <>
            <div className="flex flex-col min-h-screen">

                <main className="flex-grow">
                    <div className="container mx-auto p-4 mt-10">
                        <h1 className="text-3xl font-bold mb-10">Tous Mes Voyages Réservés</h1>



                        <div className="overflow-x-auto mb-10">
                            <table className="min-w-full bg-white border border-gray-200 rounded-lg shadow-md">
                                <thead>
                                <tr className="bg-blue-100">
                                    <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">#</th>
                                    <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">ID</th>
                                    <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">Nom</th>
                                    <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">Prix</th>
                                    <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">Description</th>
                                    <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">Etat</th>
                                    <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">Action</th>
                                </tr>
                                </thead>
                                <tbody>
                                {pieces.map((piece, index) => (
                                    <tr key={piece.id} className={index % 2 === 1 ? 'bg-blue-50' : 'bg-white'}>
                                        <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">{index + 1}</td>
                                        <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">{piece.id_piece}</td>
                                        <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">{piece.nom_piece}</td>
                                        <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">{piece.prix_piece}</td>
                                        <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">{piece.description_piece}</td>
                                        <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">{piece.etat}</td>
                                        <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap"></td>
                                        <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">
                                            {piece.etat === 'Réservé' && (
                                                <button className="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded"
                                                        onClick={() => handleCancel(piece.id_piece)}>
                                                    Annuler
                                                </button>
                                            )}
                                        </td>
                                    </tr>
                                ))}
                                </tbody>
                            </table>
                        </div>
                        <div className="flex justify-end">
                            <button
                                className="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded"
                                onClick={handleShowCanceledPieces}
                            >
                                {showCanceledPieces
                                    ? 'Masquer mes voyages annulés'
                                    : 'Consulter mes voyages annulés'}
                            </button>
                        </div>

                        {showCanceledPieces && (
                            <div className="overflow-x-auto mb-10">
                                <table className="min-w-full bg-white border border-gray-200 rounded-lg shadow-md">
                                    <thead>
                                    <tr className="bg-blue-100">
                                        <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">
                                            #
                                        </th>
                                        <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">
                                            ID
                                        </th>
                                        <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">
                                            Nom
                                        </th>
                                        <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">
                                            Prix
                                        </th>
                                        <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">
                                            Description
                                        </th>
                                        <th className="border-b border-gray-300 px-6 py-3 text-left text-xs font-bold text-gray-600 uppercase tracking-wider">
                                            Etat
                                        </th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    {canceledPieces.map((piece, index) => (
                                        <tr key={piece.id_piece}className={index % 2 === 1 ? 'bg-blue-50' : 'bg-white'}>
                                            <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">
                                                {index + 1}
                                            </td>
                                            <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">
                                                {piece.id_piece}
                                            </td>
                                            <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">
                                                {piece.nom_piece}
                                            </td>
                                            <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">
                                                {piece.prix_piece}
                                            </td>
                                            <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">
                                                {piece.description_piece}
                                            </td>
                                            <td className="border-b border-gray-300 px-6 py-4 whitespace-nowrap">
                                                {piece.etat}
                                            </td>
                                        </tr>
                                    ))}
                                    </tbody>
                                </table>
                            </div>
                        )}
                    </div>
                </main>

            </div>
        </>
    );
    }

    export default App;



    /*import './App.css';

    function App() {
      return (
        <div className="App">
          <header className="App-header">
            <h1>Welcome to My App</h1>
            <div className="login-container">
              <h2>Log In</h2>
              <form>
                <label>
                  Username:
                  <input type="text" name="username" />
                </label>
                <label>
                  Password:
                  <input type="password" name="password" />
                </label>
                <button type="submit">Log In</button>
              </form>
            </div>
          </header>
        </div>
      );
    }
}
    */
export default function (){
    return (
        <div> Historique</div>
    )
}