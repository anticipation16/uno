memberSearchIndex = [{"p":"model","c":"CardSet","l":"add(Card)","u":"add(model.Card)"},{"p":"model","c":"Pile","l":"addCard(Card)","u":"addCard(model.Card)"},{"p":"model","c":"Game","l":"addPlayer(Player)","u":"addPlayer(model.Player)"},{"p":"model","c":"CardUtility","l":"areBothColoredAndHaveSameColor(Card, Card)","u":"areBothColoredAndHaveSameColor(model.Card,model.Card)"},{"p":"model","c":"CardUtility","l":"areBothNumberedAndHaveSameNumber(Card, Card)","u":"areBothNumberedAndHaveSameNumber(model.Card,model.Card)"},{"p":"model","c":"CardUtility","l":"areBothSpecialAndHaveSameSpeciality(Card, Card)","u":"areBothSpecialAndHaveSameSpeciality(model.Card,model.Card)"},{"p":"server","c":"GameServer","l":"askPlayer(String, Player)","u":"askPlayer(java.lang.String,model.Player)"},{"p":"server","c":"PlayerThread","l":"askQuestion(String)","u":"askQuestion(java.lang.String)"},{"p":"model","c":"Color","l":"BLUE"},{"p":"server","c":"GameServer","l":"broadcast(String)","u":"broadcast(java.lang.String)"},{"p":"server","c":"GameServer","l":"broadcastMessage1ToOthersMessage2ToPlayer(String, String, Player)","u":"broadcastMessage1ToOthersMessage2ToPlayer(java.lang.String,java.lang.String,model.Player)"},{"p":"server","c":"GameServer","l":"broadcastToAllExcept(String, Player)","u":"broadcastToAllExcept(java.lang.String,model.Player)"},{"p":"model","c":"CardSet","l":"CardSet()","u":"%3Cinit%3E()"},{"p":"model","c":"ColoredCard","l":"ColoredCard(Color)","u":"%3Cinit%3E(model.Color)"},{"p":"model","c":"CardSet","l":"contains(Card)","u":"contains(model.Card)"},{"p":"model","c":"Speciality","l":"DRAW2"},{"p":"server","c":"GameServer","l":"endConnections()"},{"p":"model","c":"NumberedCard","l":"equals(Object)","u":"equals(java.lang.Object)"},{"p":"model","c":"SpecialColoredCard","l":"equals(Object)","u":"equals(java.lang.Object)"},{"p":"client","c":"GameClient","l":"execute()"},{"p":"server","c":"GameServer","l":"execute()"},{"p":"model","c":"GameStatus","l":"FINISHED"},{"p":"model","c":"Game","l":"Game(int, GameServer)","u":"%3Cinit%3E(int,server.GameServer)"},{"p":"client","c":"GameClient","l":"GameClient(String, int)","u":"%3Cinit%3E(java.lang.String,int)"},{"p":"server","c":"GameServer","l":"GameServer(int, int)","u":"%3Cinit%3E(int,int)"},{"p":"model","c":"CardUtility","l":"getAllCardsOfColorInUNODeck(Color)","u":"getAllCardsOfColorInUNODeck(model.Color)"},{"p":"model","c":"Player","l":"getCardSet()"},{"p":"model","c":"ColoredCard","l":"getColor()"},{"p":"model","c":"Game","l":"getCurrentPlayer()"},{"p":"model","c":"Game","l":"getCurrentPlayerIndex()"},{"p":"model","c":"Game","l":"getDiscardPile()"},{"p":"model","c":"Game","l":"getDrawPile()"},{"p":"model","c":"Game","l":"getGameServer()"},{"p":"model","c":"Game","l":"getMaxPlayers()"},{"p":"model","c":"Player","l":"getName()"},{"p":"model","c":"Game","l":"getNextPlayer()"},{"p":"model","c":"NumberedCard","l":"getNumber()"},{"p":"server","c":"PlayerThread","l":"getPlayer()"},{"p":"model","c":"Game","l":"getPlayers()"},{"p":"model","c":"SpecialColoredCard","l":"getSpeciality()"},{"p":"model","c":"Game","l":"getStatus()"},{"p":"model","c":"Color","l":"GREEN"},{"p":"model","c":"NumberedCard","l":"hashCode()"},{"p":"model","c":"SpecialColoredCard","l":"hashCode()"},{"p":"exceptions","c":"IllegalCardStringException","l":"IllegalCardStringException(String)","u":"%3Cinit%3E(java.lang.String)"},{"p":"exceptions","c":"IllegalMoveException","l":"IllegalMoveException(String)","u":"%3Cinit%3E(java.lang.String)"},{"p":"model","c":"GameStatus","l":"IN_PROGRESS"},{"p":"model","c":"Game","l":"incrementCurrentPlayerIndex()"},{"p":"model","c":"CardSet","l":"isEmpty()"},{"p":"client","c":"GameClient","l":"main(String[])","u":"main(java.lang.String[])"},{"p":"server","c":"GameServer","l":"main(String[])","u":"main(java.lang.String[])"},{"p":"server","c":"GameServer","l":"messagePlayer(String, Player)","u":"messagePlayer(java.lang.String,model.Player)"},{"p":"model","c":"NumberedCard","l":"NumberedCard(Color, int)","u":"%3Cinit%3E(model.Color,int)"},{"p":"model","c":"Pile","l":"peekTopCard()"},{"p":"model","c":"Pile","l":"Pile()","u":"%3Cinit%3E()"},{"p":"model","c":"Pile","l":"Pile(List<Card>)","u":"%3Cinit%3E(java.util.List)"},{"p":"model","c":"Player","l":"Player(String)","u":"%3Cinit%3E(java.lang.String)"},{"p":"server","c":"PlayerThread","l":"PlayerThread(Socket, GameServer)","u":"%3Cinit%3E(java.net.Socket,server.GameServer)"},{"p":"model","c":"Pile","l":"popTopCard()"},{"p":"interaction","c":"MoveProcessor","l":"processMove(Game, Player, String)","u":"processMove(model.Game,model.Player,java.lang.String)"},{"p":"model","c":"Game","l":"processMove(Player, String)","u":"processMove(model.Player,java.lang.String)"},{"p":"server","c":"GameServer","l":"processMove(PlayerThread, String)","u":"processMove(server.PlayerThread,java.lang.String)"},{"p":"server","c":"GameServer","l":"processNewPlayer(Player, PlayerThread)","u":"processNewPlayer(model.Player,server.PlayerThread)"},{"p":"model","c":"Color","l":"RED"},{"p":"model","c":"CardSet","l":"remove(Card)","u":"remove(model.Card)"},{"p":"model","c":"Speciality","l":"REVERSE"},{"p":"server","c":"PlayerThread","l":"run()"},{"p":"server","c":"PlayerThread","l":"sendMessage(String)","u":"sendMessage(java.lang.String)"},{"p":"model","c":"Player","l":"setCardSet(CardSet)","u":"setCardSet(model.CardSet)"},{"p":"model","c":"Game","l":"setCurrentPlayerIndex(int)"},{"p":"model","c":"Game","l":"setStatus(GameStatus)","u":"setStatus(model.GameStatus)"},{"p":"model","c":"Pile","l":"shuffle()"},{"p":"model","c":"CardSet","l":"size()"},{"p":"model","c":"Speciality","l":"SKIP"},{"p":"model","c":"SpecialColoredCard","l":"SpecialColoredCard(Color, Speciality)","u":"%3Cinit%3E(model.Color,model.Speciality)"},{"p":"model","c":"Game","l":"start()"},{"p":"model","c":"CardUtility","l":"stringRepToCard(String)","u":"stringRepToCard(java.lang.String)"},{"p":"model","c":"CardSet","l":"toString()"},{"p":"model","c":"NumberedCard","l":"toString()"},{"p":"model","c":"Player","l":"toString()"},{"p":"model","c":"SpecialColoredCard","l":"toString()"},{"p":"model","c":"CardUtility","l":"UNO_MAX_NUMBER"},{"p":"model","c":"CardUtility","l":"UNO_MIN_NUMBER"},{"p":"model","c":"GameStatus","l":"UNO_PENDING_MODE"},{"p":"model","c":"Color","l":"valueOf(String)","u":"valueOf(java.lang.String)"},{"p":"model","c":"GameStatus","l":"valueOf(String)","u":"valueOf(java.lang.String)"},{"p":"model","c":"Speciality","l":"valueOf(String)","u":"valueOf(java.lang.String)"},{"p":"model","c":"Color","l":"values()"},{"p":"model","c":"GameStatus","l":"values()"},{"p":"model","c":"Speciality","l":"values()"},{"p":"model","c":"GameStatus","l":"WAITING_FOR_PLAYERS"},{"p":"model","c":"Color","l":"YELLOW"}];updateSearchResults();