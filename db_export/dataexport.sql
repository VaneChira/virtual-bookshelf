-- MySQL dump 10.13  Distrib 8.0.26, for Win64 (x86_64)
--
-- Host: localhost    Database: bookstore
-- ------------------------------------------------------
-- Server version	8.0.26

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `author`
--

DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `author` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(250) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `author`
--

LOCK TABLES `author` WRITE;
/*!40000 ALTER TABLE `author` DISABLE KEYS */;
INSERT INTO `author` VALUES (1,'Dan Brown ','Dan Brown is the author of numerous #1 bestselling novels, including The Da Vinci Code, which has become one of the best selling novels of all time as well as the subject of intellectual debate among readers and scholars. Brown’s novels are published in 52 languages around the world with 200 million copies in print. \nIn 2005, Brown was named one of the 100 Most Influential People in the World by TIME Magazine, whose editors credited him with “keeping the publishing industry afloat; renewed interest in Leonardo da Vinci and early Christian history; spiking tourism to Paris and Rome; a growing membership in secret societies; the ire of Cardinals in Rome; eight books denying the claims of the novel and seven guides to read along with it; a flood of historical thrillers; and a major motion picture franchise.”'),(2,'Veronica Roth','Veronica Roth is the #1 New York Times best-selling author of the Divergent series (Divergent, Insurgent, Allegiant, and Four: A Divergent Collection), the Carve the Mark duology (Carve the Mark, the Fates Divide), The End and Other Beginnings collection of short fiction, and many short stories and essays. Her first book for adult audiences, Chosen Ones, is out now. She lives in Chicago.'),(3,'James Patterson','James Patterson is the world’s bestselling author and most trusted storyteller. He has created more enduring fictional characters than any other novelist writing today, with his Alex Cross, Michael Bennett, Women’s Murder Club, Private, NYPD Red, Daniel X, Maximum Ride, and Middle School series. He has sold over 380 million books worldwide and currently holds the Guinness World Record for the most #1 New York Times bestsellers. In addition to writing the thriller novels for which he is best known, among them The President Is Missing with President Bill Clinton, Patterson also writes fiction for young readers of all ages, including the Max Einstein series, produced in partnership with the Albert Einstein Estate. He is also the first author to have #1 new titles simultaneously on the New York Times adult and children’s bestseller lists.'),(4,'Brianna Wiest','Brianna Wiest is an American writer and poet. She is best known for her prolific work on emotional intelligence. Wiest graduated from Elizabethtown College. Her published works include the books 101 Essays to Change the Way You Think, The Truth About Everything, and The Human Element, all of which are composed of essays that she wrote for the Thought Catalog website. She also has a poetry book Salt Water published by TC Books.'),(5,'George R.R. Martin','George Raymond Richard Martin, also known as GRRM, is an American novelist and short story writer, screenwriter, and television producer. He is the author of the series of epic fantasy novels A Song of Ice and Fire, which was adapted into the Emmy Award-winning HBO series Game of Thrones. In 2005, Lev Grossman of Time called Martin \"the American Tolkien\",[4] and in 2011, he was included on the annual Time 100 list of the most influential people in the world.'),(6,'Cassandra Clare','Cassandra Clare was born overseas and spent her early years traveling around the world with her family and several trunks of fantasy books. Cassandra worked for several years as an entertainment journalist for the Hollywood Reporter before turning her attention to fiction. She is the author of City of Bones, the first book in the Mortal Instruments trilogy and a New York Times bestseller. Cassandra lives with her fiance and their two cats in Massachusetts.');
/*!40000 ALTER TABLE `author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `book`
--

DROP TABLE IF EXISTS `book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `book` (
  `id` int NOT NULL AUTO_INCREMENT,
  `book_title` varchar(100) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  `rating` float DEFAULT NULL,
  `number_of_ratings` int DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `pages` int DEFAULT NULL,
  `year` int DEFAULT NULL,
  `language` varchar(70) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `book`
--

LOCK TABLES `book` WRITE;
/*!40000 ALTER TABLE `book` DISABLE KEYS */;
INSERT INTO `book` VALUES (1,'Origin','Robert Langdon, Harvard professor of symbology and religious iconology, arrives at the ultramodern Guggenheim Museum Bilbao to attend a major announcement—the unveiling of a discovery that “will change the face of science forever.” The evening’s host is Edmond Kirsch, a forty-year-old billionaire and futurist whose dazzling high-tech inventions and audacious predictions have made him a renowned global figure. Kirsch, who was one of Langdon’s first students at Harvard two decades earlier, is about to reveal an astonishing breakthrough . . . one that will answer two of the fundamental questions of human existence.',NULL,NULL,'https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fcdn1.dol.ro%2Fslir%2Fw600%2Fdol.ro%2Fcs-content%2Fcs-photos%2Fproducts%2Foriginal%2Forigin---dan-brown_244929_1_1507664548.jpeg&f=1&nofb=1',456,2017,'English'),(2,'Divergent (Divergent #1)','In Beatrice Prior\'s dystopian Chicago world, society is divided into five factions, each dedicated to the cultivation of a particular virtue—Candor (the honest), Abnegation (the selfless), Dauntless (the brave), Amity (the peaceful), and Erudite (the intelligent). On an appointed day of every year, all sixteen-year-olds must select the faction to which they will devote the rest of their lives. For Beatrice, the decision is between staying with her family and being who she really is—she can\'t have both. So she makes a choice that surprises everyone, including herself.',NULL,NULL,'https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fimages.huffingtonpost.com%2F2014-03-04-divergentbyveronicaroth.jpg&f=1&nofb=1',487,2012,'English'),(3,'Four Blind Mice','Detective Alex Cross is on his way to resign from the Washington, D.C., Police Force when his partner shows up at his door with a case he can\'t refuse. One of John Sampson\'s oldest friends, from their days in Vietnam, has been arrested for murder. Worse yet, he is subject to the iron hand of the United States Army. The evidence against him is strong enough to send him to the gas chamber but Sampson is certain his friend has been framed.Drawing on their years of street training and an almost telepathic mutual trust, Cross and Sampson go deep behind military lines to confront the most terrifying-and deadly-killers they have ever encountered.On his visits home, Alex must confront another, more harrowing mystery: what\'s the matter with Nana Mama? ',NULL,NULL,'https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fcovers.openlibrary.org%2Fw%2Fid%2F189670-L.jpg&f=1&nofb=1',416,2003,'English'),(4,'101 Essays That Will Change The Way You Think','Over the past few years, Brianna Wiest has gained renown for her deeply moving, philosophical writing. This new compilation of her published work features pieces on why you should pursue purpose over passion, embrace negative thinking, see the wisdom in daily routine, and become aware of the cognitive biases that are creating the way you see your life. Some of these pieces have never been seen; others have been read by millions of people around the world. Regardless, each will leave you thinking: this idea changed my life.',NULL,NULL,'https://i.gr-assets.com/images/S/compressed.photo.goodreads.com/books/1479346785l/32998876._SY475_.jpg',334,2016,'English'),(5,'Insurgent (Divergent #2)','One choice can transform you—or it can destroy you. But every choice has consequences, and as unrest surges in the factions all around her, Tris Prior must continue trying to save those she loves—and herself—while grappling with haunting questions of grief and forgiveness, identity and loyalty, politics and love. Tris\'s initiation day should have been marked by celebration and victory with her chosen faction; instead, the day ended with unspeakable horrors. War now looms as conflict between the factions and their ideologies grows. And in times of war, sides must be chosen, secrets will emerge, and choices will become even more irrevocable—and even more powerful. Transformed by her own decisions but also by haunting grief and guilt, radical new discoveries, and shifting relationships, Tris must fully embrace her Divergence, even if she does not know what she may lose by doing so.',NULL,NULL,'https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fi.pinimg.com%2F736x%2Ff4%2Fb4%2F0d%2Ff4b40dc4513b44709a5e6f8e2892d419--divergent-insurgent-allegiant-divergent-series.jpg&f=1&nofb=1',525,2012,'English'),(6,'Allegiant (Divergent #3)','The faction-based society that Tris Prior once believed in is shattered - fractured by violence and power struggles and scarred by loss and betrayal. So when offered a chance to explore the world past the limits she\'s known, Tris is ready. Perhaps beyond the fence, she and Tobias will find a simple new life together, free from complicated lies, tangled loyalties, and painful memories. But Tris\'s new reality is even more alarming than the one she left behind. Old discoveries are quickly rendered meaningless. Explosive new truths change the hearts of those she loves. And once again, Tris must battle to comprehend to complexities of human nature - and of herself - while facing impossible choices about courage, allegiance, sacrifice, and love.',NULL,NULL,'https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2F3.bp.blogspot.com%2F-TytVkxQICBc%2FUYwC_puT1MI%2FAAAAAAAABhQ%2FLHKuXur54ms%2Fs1600%2FALLEGIANT.jpg&f=1&nofb=1',526,2013,'English'),(7,'A Game of Thrones (A Song of Ice and Fire #1)','Long ago, in a time forgotten, a preternatural event threw the seasons out of balance. In a land where summers can last decades and winters a lifetime, trouble is brewing. The cold is returning, and in the frozen wastes to the north of Winterfell, sinister and supernatural forces are massing beyond the kingdom’s protective Wall. At the center of the conflict lie the Starks of Winterfell, a family as harsh and unyielding as the land they were born to. Sweeping from a land of brutal cold to a distant summertime kingdom of epicurean plenty, here is a tale of lords and ladies, soldiers and sorcerers, assassins and bastards, who come together in a time of grim omens. Here an enigmatic band of warriors bear swords of no human metal; a tribe of fierce wildlings carry men off into madness; a cruel young dragon prince barters his sister to win back his throne; and a determined woman undertakes the most treacherous of journeys.',NULL,NULL,'https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fiv1.lisimg.com%2Fimage%2F6326008%2F600full-a-game-of-thrones-(a-song-of-ice-and-fire%252C-book-1)-.jpg&f=1&nofb=1',835,1996,'English'),(8,'A Clash of Kings (A Song of Ice and Fire #2)','A comet the color of blood and flame cuts across the sky. Two great leaders—Lord Eddard Stark and Robert Baratheon—who hold sway over an age of enforced peace are dead, victims of royal treachery. Now, from the ancient citadel of Dragonstone to the forbidding shores of Winterfell, chaos reigns. Six factions struggle for control of a divided land and the Iron Throne of the Seven Kingdoms, preparing to stake their claims through tempest, turmoil, and war. It is a tale in which brother plots against brother and the dead rise to walk in the night. Here a princess masquerades as an orphan boy; a knight of the mind prepares a poison for a treacherous sorceress; and wild men descend from the Mountains of the Moon to ravage the countryside. Against a backdrop of incest and fratricide, alchemy and murder, victory may go to the men and women possessed of the coldest steel...and the coldest hearts. For when kings clash, the whole land trembles.',NULL,NULL,'https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fwww.randomhousebooks.com%2Fwp-content%2Fuploads%2F2015%2F02%2F9780345535412.jpg&f=1&nofb=1',969,2002,'English'),(9,'A Storm of Swords (A Song of Ice and Fire #3)','Of the five contenders for power, one is dead, another in disfavor, and still the wars rage as alliances are made and broken. Joffrey sits on the Iron Throne, the uneasy ruler of the Seven Kingdoms. His most bitter rival, Lord Stannis, stands defeated and disgraced, victim of the sorceress who holds him in her thrall. Young Robb still rules the North from the fortress of Riverrun. Meanwhile, making her way across a blood-drenched continent is the exiled queen, Daenerys, mistress of the only three dragons still left in the world. And as opposing forces manoeuver for the final showdown, an army of barbaric wildlings arrives from the outermost limits of civilization, accompanied by a horde of mythical Others—a supernatural army of the living dead whose animated corpses are unstoppable. As the future of the land hangs in the balance, no one will rest until the Seven Kingdoms have exploded in a veritable storm of swords.',NULL,NULL,'https://external-content.duckduckgo.com/iu/?u=http%3A%2F%2Fimg1.fantasticfiction.co.uk%2Fimages%2Fn4%2Fn23012.jpg&f=1&nofb=1',1177,2003,'English'),(10,'A Feast for Crows (A Song of Ice and Fire #4)','Bloodthirsty, treacherous and cunning, the Lannisters are in power on the Iron Throne in the name of the boy-king Tommen. The war in the Seven Kingdoms has burned itself out, but in its bitter aftermath new conflicts spark to life. The Martells of Dorne and the Starks of Winterfell seek vengeance for their dead. Euron Crow\'s Eye, as black a pirate as ever raised a sail, returns from the smoking ruins of Valyria to claim the Iron Isles. From the icy north, where Others threaten the Wall, apprentice Maester Samwell Tarly brings a mysterious babe in arms to the Citadel. Against a backdrop of incest and fratricide, alchemy and murder, victory will go to the men and women possessed of the coldest steel and the coldest hearts.',NULL,NULL,'https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fs3.amazonaws.com%2Fbrisk-listing-original%2FBNO652OXWU-1491823666.jpg&f=1&nofb=1',1061,2011,'English'),(11,'Queen of Air and Darkness (The Dark Artifices #3)','Innocent blood has been spilled on the steps of the Council Hall, the sacred stronghold of the Shadowhunters. In the wake of the tragic death of Livia Blackthorn, the Clave teeters on the brink of civil war. One fragment of the Blackthorn family flees to Los Angeles, seeking to discover the source of the disease that is destroying the race of warlocks. Meanwhile, Julian and Emma take desperate measures to put their forbidden love aside and undertake a perilous mission to Faerie to retrieve the Black Volume of the Dead. What they find in the Courts is a secret that may tear the Shadow World asunder and open a dark path into a future they could never have imagined. Caught in a race against time, Emma and Julian must save the world of Shadowhunters before the deadly power of the parabatai curse destroys them and everyone they love.',NULL,NULL,'https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Fthemortalinstrumentssource.files.wordpress.com%2F2017%2F11%2Fqueen-of-air-and-darkness.jpg%3Fw%3D620&f=1&nofb=1',912,2018,'English');
/*!40000 ALTER TABLE `book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `books_author`
--

DROP TABLE IF EXISTS `books_author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `books_author` (
  `author_id` int NOT NULL,
  `book_id` int NOT NULL,
  PRIMARY KEY (`author_id`,`book_id`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `books_author_ibfk_1` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`),
  CONSTRAINT `books_author_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `books_author`
--

LOCK TABLES `books_author` WRITE;
/*!40000 ALTER TABLE `books_author` DISABLE KEYS */;
INSERT INTO `books_author` VALUES (1,1),(2,2),(3,3),(4,4),(2,5),(2,6),(5,7),(5,8),(5,9),(5,10),(6,11);
/*!40000 ALTER TABLE `books_author` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `user_id` int NOT NULL,
  `book_id` int NOT NULL,
  `rating` int DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`user_id`,`book_id`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `feedback_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `feedback`
--

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;
INSERT INTO `feedback` VALUES (1,1,4,'asdsd','2021-08-29'),(1,3,4,'Intriguing story. Unexpected end','2021-08-21'),(1,7,5,'Amazing so far','2021-08-25'),(4,1,4,'asdsad','2021-08-29'),(4,6,4,'mama manu','2021-08-29');
/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genres`
--

DROP TABLE IF EXISTS `genres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genres` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` varchar(250) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genres`
--

LOCK TABLES `genres` WRITE;
/*!40000 ALTER TABLE `genres` DISABLE KEYS */;
INSERT INTO `genres` VALUES (1,'History','History is one of the three main genres in Western theatre alongside tragedy and comedy, although it originated, in its modern form, thousands of years later than the other primary genres. For this reason, it is often treated as a subset of tragedy. A play in this genre is known as a history play and is based on a historical narrative, often set in the medieval or early modern past. History emerged as a distinct genre from tragedy in Renaissance England.It is a field of research which uses a narrative to examine and analyse the sequence of events, and it sometimes attempts to investigate objectively the patterns of cause and effect that determine events.'),(2,'Mystery','Mystery fiction is a loosely-defined term that is often used as a synonym of detective fiction — in other words a novel or short story in which a detective (either professional or amateur) solves a crime. The term \"mystery fiction\" may sometimes be limited to the subset of detective stories in which the emphasis is on the puzzle element and its logical solution (cf. whodunit), as a contrast to hardboiled detective stories which focus on action and gritty realism. However, in more general usage \"mystery\" may be used to describe any form of crime fiction, even if there is no mystery to be solved. For example, the Mystery Writers of America describes itself as \"the premier organization for mystery writers, professionals allied to the crime writing field, aspiring crime writers, and those who are devoted to the genre\".'),(3,'Psychology',' Books in the psychology nonfiction genre are about the applied discipline that involves the scientific study of mental function and behaviors. The books in this genre deal with the science of understanding individuals’ mind functions in an attempt to benefit society. They attempt to tackle the understanding of the inner mind and apply treatments to improve roles of social, behavioral, and cognitive health.'),(4,'Romance','According to the Romance Writers of America, \"Two basic elements comprise every romance novel: a central love story and an emotionally-satisfying and optimistic ending.\" Both the conflict and the climax of the novel should be directly related to that core theme of developing a romantic relationship, although the novel can also contain subplots that do not specifically relate to the main characters\' romantic love. Other definitions of a romance novel may be broader, including other plots and endings or more than two people, or narrower, restricting the types of romances or conflicts.'),(5,'Self-Help','Self-help, or self-improvement, is a self-guided improvement—economically, intellectually, or emotionally—often with a substantial psychological basis. Many different self-help groupings exist and each has its own focus, techniques, associated beliefs, proponents and in some cases, leaders. \"Self-help culture, particularly Twelve-Step culture, has provided some of our most robust new language: recovery, dysfunctional families, and codependency.'),(6,'Spirituality','Book in the spirituality nonfiction genre are about the belief and processes of personal transformation involved with believing in a power greater than one’s self, over the universe. The books in this genre involve techniques and journeys about finding and believing in something beyond one’s self, and becoming linked with something bigger (a god or deity). They don’t necessarily have to do with religion, because spirituality is often separated from (but sometimes connected with) religion in that it’s based on subjective experience and psychological growth.'),(7,'Thriller','Thrillers are characterized by fast pacing, frequent action, and resourceful heroes who must thwart the plans of more-powerful and better-equipped villains. Literary devices such as suspense, red herrings and cliffhangers are used extensively. Thrillers often overlap with mystery stories, but are distinguished by the structure of their plots. In a thriller, the hero must thwart the plans of an enemy, rather than uncover a crime that has already happened. Thrillers also occur on a much grander scale: the crimes that must be prevented are serial or mass murder, terrorism, assassination, or the overthrow of governments. Jeopardy and violent confrontations are standard plot elements. While a mystery climaxes when the mystery is solved, a thriller climaxes when the hero finally defeats the villain, saving his own life and often the lives of others.'),(8,'Young Adult','Young-adult fiction, whether in the form of novels or short stories, has distinct attributes that distinguish it from the other age categories of fiction. The vast majority of YA stories portray an adolescent as the protagonist, rather than an adult or a child. The subject matter and story lines are typically consistent with the age and experience of the main character, but beyond that YA stories span the entire spectrum of fiction genres. The settings of YA stories are limited only by the imagination and skill of the author. Themes in YA stories often focus on the challenges of youth, so much so that the entire age category is sometimes referred to as problem novels or coming of age novel. '),(9,'Drama','The drama genre is strongly based in a character, or characters, that are in conflict at a crucial moment in their lives. Most dramas revolve around families and often have tragic or painful resolutions. Drama depends a lot on realistic characters dealing with emotional themes, such as: alcoholism, drug addiction, infidelity, morals, racism, religion, intolerance, sexuality, poverty, class issues, violence, and corruption (society or natural disasters can even be thrown in from time to time). Drama often crosses over and meshes with other genres.'),(10,'Horror','Horror fiction is fiction in any medium intended to scare, unsettle, or horrify the audience. Historically, the cause of the \"horror\" experience has often been the intrusion of a supernatural element into everyday human experience. Since the 1960s, any work of fiction with a morbid, gruesome, surreal, or exceptionally suspenseful or frightening theme has come to be called \"horror\". Horror fiction often overlaps science fiction or fantasy, all three of which categories are sometimes placed under the umbrella classification speculative fiction.'),(11,'Fiction','Fiction is the telling of stories which are not real. More specifically, fiction is an imaginative form of narrative, one of the four basic rhetorical modes. Although the word fiction is derived from the Latin fingo, fingere, finxi, fictum, \"to form, create\", works of fiction need not be entirely imaginary and may include real people, places, and events. Fiction may be either written or oral. Although not all fiction is necessarily artistic, fiction is largely perceived as a form of art or entertainment. The ability to create fiction and other artistic works is considered to be a fundamental aspect of human culture, one of the defining characteristics of humanity.'),(12,'Fantasy','Fantasy is a genre that uses magic and other supernatural forms as a primary element of plot, theme, and/or setting. Fantasy is generally distinguished from science fiction and horror by the expectation that it steers clear of technological and macabre themes, respectively, though there is a great deal of overlap between the three (collectively known as speculative fiction or science fiction/fantasy).In its broadest sense, fantasy comprises works by many writers, artists, filmmakers, and musicians, from ancient myths and legends to many recent works embraced by a wide audience today, including young adults, most of whom are represented by the works below. '),(13,'Autobiography','Autobiography is a style of writing that has been around nearly as long as history has been recorded. Yet autobiography was not classified as a genre within itself until the late eighteenth century. Most autobiographies are written from the first person singular perspective. This is fitting because autobiography is usually a story one tells about oneself. It would not naturally follow then that the writer would recount his or her past from a second or third person perspective. The author, the narrator, and the protagonist must share a common identity for the work to be considered an autobiography.'),(14,'Detective',' Books in the detective genre are about a detective, or detectives – either a professional or amateur – who is involved in solving a crime. The detective sometimes has to figure out how the crime was actually pulled off/committed in order to solve the case. The plot follows the detective as they discover clues that lead to the apprehension of the criminal. The detective genre is closely linked and similar to the crime and mystery genres.'),(15,'Crime','The crime genre includes the broad selection of books on criminals and the court system, but the most common focus is investigations and sleuthing. Mystery novels are usually placed into this category, although there is a separate division for \"crime\". It  involves a crime in some way: a crime being committed, or having been committed. A crime story can also be about a criminal’s life. Often crime fiction crosses over or meshes with the suspense, thriller, detective, mystery, action, and/or adventure genres. '),(16,'',NULL);
/*!40000 ALTER TABLE `genres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genres_in_books`
--

DROP TABLE IF EXISTS `genres_in_books`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `genres_in_books` (
  `genre_id` int NOT NULL,
  `book_id` int NOT NULL,
  PRIMARY KEY (`genre_id`,`book_id`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `genres_in_books_ibfk_1` FOREIGN KEY (`genre_id`) REFERENCES `genres` (`id`),
  CONSTRAINT `genres_in_books_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genres_in_books`
--

LOCK TABLES `genres_in_books` WRITE;
/*!40000 ALTER TABLE `genres_in_books` DISABLE KEYS */;
INSERT INTO `genres_in_books` VALUES (7,1),(14,1),(8,2),(11,2),(7,3),(14,3),(5,4),(8,5),(11,5),(8,6),(11,6),(2,7),(9,7),(11,7),(2,8),(9,8),(11,8),(2,9),(9,9),(11,9),(2,10),(9,10),(11,10),(4,11),(8,11),(12,11);
/*!40000 ALTER TABLE `genres_in_books` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ROLE_USER'),(2,'ROLE_ADMIN');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `last_name` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'Vanessa','Chira','vanechira@gmail.com','$2a$10$CHt5f6X5HOJesdWxIbeFQeci5JW5ZQ9Whn9vYdeztZgjuAi595yw2'),(2,'Anne','Smith','anne@yahoo.com','$2a$10$jYtEdsFsP1OuL9nKHGfzHujXz/Tad7R2r5v98O2F9buyn9ejAS6Z.'),(3,'Jessica','Anderson','jessica@gmail.com','$2a$10$l/qa/IgAjqzwyGW2Cv/XHeBjOH.2gDaRIlxvTWuqFYJg0MDAJLl/W'),(4,'Andrei','Vlad','andrei@gmail.com','$2a$10$s.pPKuEvFb07mMN6od4fsObjS92k9STfWn1p7ZXbN3MzVfgkVCd1C');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_book`
--

DROP TABLE IF EXISTS `user_book`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_book` (
  `user_id` int NOT NULL,
  `book_id` int NOT NULL,
  `progress_pages` int DEFAULT NULL,
  `book_state` tinyint DEFAULT NULL,
  PRIMARY KEY (`user_id`,`book_id`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `user_book_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_book_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `book` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_book`
--

LOCK TABLES `user_book` WRITE;
/*!40000 ALTER TABLE `user_book` DISABLE KEYS */;
INSERT INTO `user_book` VALUES (1,1,NULL,NULL),(1,2,0,1),(1,3,0,1),(1,4,0,1),(1,5,0,1),(1,6,0,1),(1,7,455,2),(1,8,0,1),(1,9,656,2),(1,10,NULL,2),(1,11,912,3),(4,1,NULL,2),(4,11,NULL,NULL);
/*!40000 ALTER TABLE `user_book` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_role`
--

DROP TABLE IF EXISTS `user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `user_role_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_role_ibfk_2` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_role`
--

LOCK TABLES `user_role` WRITE;
/*!40000 ALTER TABLE `user_role` DISABLE KEYS */;
INSERT INTO `user_role` VALUES (1,1,1),(2,2,1),(3,3,1),(4,4,2);
/*!40000 ALTER TABLE `user_role` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-08-29  0:30:53
