# FYProject

April 18, 2018.

As mentioned previously, the intention is to update this `README` as the project progresses. It is also a good place to keep a changelog or ideas.

The project has been updated with the following changes:

* A file `Visualize.java` has been added, which contains the currently implemented visualization functionality. The application can currently draw the RNA string as a sequence, including matches, and as a circle representation, also including matches. However, there are some known issues:
  * When matches are drawn in the sequence visualization, lines currently overlap, which is not what is intended, so we need to look into a fix for that.
  * With the circle representation lines *occationally* overlap. As with the sequence visualization, some adjustments are needed.
* The `main.java` file has been updated to include a temporary implementation of the left toolbar, such that sequence visualization and circle visualization can be toggled on and off. This change has currently disabled the Matrix view.
* Tiny images have been added.


The repository still contain the following files:

* `Main.java` contains the runnable part of the application, so you should open this file in IntelliJ and try to run it.
* `Nussinov.java` contains the implementation of the Nussinov algorithm from the slides Philipp provided.
* `CloseBox.java`, `AboutBox.java`, `LoadTextFile.java` and `Tuple.java` are all helper classes which serve different purposes, which should hopefully be fairly clear from their titles.
* `Controller.java` and `application.fxml` currently serve no purpose. These will be needed if we wish to *style* the application later. They might also be helpful in achieving other things. Philipp might be able to elaborate on this.
* `welcome.txt` and `help.txt` both contain some filler text which is loaded into the application when needed.
* `VisualizationAttempt.java` which is a runnable version of the first attempt at implementing the visualization functionalities.

*- Dennis*

---

April 11, 2018

To my fellow students 😃

Within this repository you will find the initial upload of the application. You should clone this repository into IntelliJ. Once you have done that, we can all work on the same files "simultaneously". However, please do note that we should avoid working simultaneously on the same specific parts of the code, as this may lead to conflicts with Git - and unwanted debugging! In other words, we should coordinate 😋

The repository contains a number of files, and each file should have a decent amount of comments, which should help explain what most things do. A small description of each file is given here.

* `Main.java` contains the runnable part of the application, so you should open this file in IntelliJ and try to run it.
* `Nussinov.java` contains the implementation of the Nussinov algorithm from the slides Philipp provided.
* `CloseBox.java`, `AboutBox.java`, `LoadTextFile.java` and `Tuple.java` are all helper classes which serve different purposes, which should hopefully be fairly clear from their titles.
* `Controller.java` and `application.fxml` currently serve no purpose. These will be needed if we wish to *style* the application later. They might also be helpful in achieving other things. Philipp might be able to elaborate on this.
* `welcome.txt` and `help.txt` both contain some filler text which is loaded into the application when needed.

I think that's it for now. The intention is for this `README` to be updated along the way. How frequent however, time will have to tell. 😃

*- Dennis*
