## Description

<h2>What is Centipede</h2>
Another typical arcade shooter. You fire at the bugs and mushrooms on the board to gain points.
If all lives are lost, GAME OVER!

<h2>How To Start</h2>
Either run project3_cs351.jar or (if running from source files) start the application for centipedeApp.java
Main Class: centipedeApp.java

<h2>Sprites Source</h2>
All Sprites resembling the original Centipede are Open Source, retrieved from:
https://www.spriters-resource.com/arcade/centipede/sheet/50437/

## How To Play

***Objective***
<ol>
    <li>Shoot Mushrooms</li>
    <li>Shoot Bugs</li>
    <li>Profit</li>
</ol>
The game has no end, it will play until you die (or some unforeseen error occurs).<br>
The game ends once your lives equal zero. The purpose is to rack up those points!

***Scores***
<ul>
    <li>Mushroom: 20 points (after 4 hits)</li>
    <li>Centipede Head: 300 points</li>
    <li>Centipede Body: 100 points</li>
    <li>Flea: 200 points</li>
</ul>

***Game Objects***
<ul>
    <li>
        Centipedes move from left to right, and never leave the screen.<br>
        Be careful, shooting the body of a Centipede causes it to split!
    </li>
    <li>
        Fleas move straight down, creating a line of Mushrooms in their path
    </li>
    <li>
        Mushrooms serve as bondaries for you and the centipedes.<br>
        They can cause the centipedes to move towards you faster, or you
        can use them to your advantage.
    </li>
    <li>
        <strong>You are the Bug Zapper (Ship)!</strong><br>
        The Zapper can move within the screen area, and can shoot be holding
        down the Spacebar.
        You start with Four Lives, but can gain another life every 12000 points!
    </li>
</ul>
<em>Spiders and Scorpions were not implemented due to time constraints.</em>

***Movement***
<ul>
    <li>UP: W -OR- UP-ARROW</li>
    <li>DOWN: S -OR- DOWN-ARROW</li>
    <li>LEFT: A -OR- LEFT-ARROW</li>
    <li>RIGHT: D -OR- RIGHT-ARROW</li>
    <li>SHOOT: SPACEBAR</li>
    <li>PAUSE/UNPAUSE: ESC</li>
</ul>

## Known Bugs & Non-Funtional Features
<ul>
    <li>Visual Bug: Mushrooms will not update their display sometimes. Does not affect gameplay</li>
    <li>Rare Occurances where centipede will permanently "phase out", meaning it cannot be shot.
        If this occurs, die and restart the game. (MIGHT HAVE BEEN FIXED, DIFFICULT TO REPLICATE)
    </li>
</ul>

**Other Notes from Author**
Spider and Scorpion Classes were not implemented due to time.<br>
Used a CSS file to style some elements, I did not check if this was fine to do but am hoping that it is.
