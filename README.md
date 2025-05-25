This Java program implements a graphical visualizer for the Quicksort algorithm using Swing, a GUI toolkit in Java. The application introduces users to the divide-and-conquer concept by allowing them to choose a pivot and then watch how the quicksort algorithm recursively sorts an array of numbers.
-----------------------------------------------------------------------------------------------------
Main Features:
  GUI Layout
    The program uses a JFrame as the main window titled "Divide and Conquer Algorithm - Quicksort".
    It displays headers, labels, and buttons with aesthetic colors to guide users through the process.

  Interactive Pivot Selection
    A shuffled array of 7 integers (from 1 to 30) is displayed as clickable buttons.
    Users select a pivot by clicking any of the number buttons, triggering the visual sorting.
  
  Real-time Quicksort Visualization
    Once a pivot is chosen, a copy of the array is sorted using a modified quicksort algorithm.
    The visual representation below the original array shows how elements are rearranged in each recursive step.
  
  Color highlights represent different actions
  🔶 Orange: Current pivot being evaluated.
  🟡 Yellow: Element currently being compared.
  🟢 Green: Elements that were swapped.
  🔴 Red: Final pivot placement.
  🔷 Blue (default): Normal state of other elements.
  
  Threaded Execution
    SwingWorker is used to handle sorting in a background thread to keep the UI responsive.
    Sorting steps are animated using Thread.sleep() for clear, gradual transitions.
  
  Visual Update Panels
    Each value is displayed in a panel beneath the corresponding button.
    As sorting proceeds, these panels are dynamically updated to reflect the array’s changing state.

Technical Highlights
  Uses Java Swing components such as JFrame, JLabel, JButton, and JPanel.
  Implements custom colors and layout for better user experience.
  Applies clone() on the original array to preserve it across different pivot selections.
  Makes use of lambda expressions, Streams, and modern Java practices for readability.

Purpose:
  This program is ideal for
    Students learning sorting algorithms. 
    Visual learners who benefit from graphical representations.
    Demonstrations in algorithm or data structure classes.
