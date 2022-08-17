import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.BevelBorder;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

public class CharacterChoice extends JPanel {

  public JPopupMenu popup;
  private int choice;

  public JPopupMenu getPopupMenu() {return popup;}
  
  public CharacterChoice() {
    popup = new JPopupMenu();
    ActionListener menuListener = new ActionListener() {
      public void actionPerformed(ActionEvent event) {
        if(event.getActionCommand() == "Bubbles") {
        	choice = 1;       	
        }
        
        if(event.getActionCommand() == "Blossom") {
        	choice = 2;
        }
        
        if(event.getActionCommand() == "Buttercup") {
        	choice = 3;
        }
        //System.out.println(choice);
      }
    };
    
   
    
    JMenuItem item;
    popup.add(item = new JMenuItem("Bubbles", new ImageIcon("BUBBLES.png")));
    item.setHorizontalTextPosition(JMenuItem.RIGHT);
    item.addActionListener(menuListener);
    popup.add(item = new JMenuItem("Blossom", new ImageIcon("BLOSSOM.png")));
    item.setHorizontalTextPosition(JMenuItem.RIGHT);
    item.addActionListener(menuListener);
    popup.add(item = new JMenuItem("Buttercup", new ImageIcon("BUTTERCUP.png")));
    item.setHorizontalTextPosition(JMenuItem.RIGHT);
    item.addActionListener(menuListener);
    

    popup.setBorder(new BevelBorder(BevelBorder.RAISED));
    popup.addPopupMenuListener(new PopupPrintListener());

    addMouseListener(new MousePopupListener());
  }
   
   
  public void setChoice(int c) {choice = c;}
  public int getChoice() {return choice;}
  
  //popup trigger
  class MousePopupListener extends MouseAdapter {
    public void mousePressed(MouseEvent e) {
      checkPopup(e);
    }

    public void mouseClicked(MouseEvent e) {
      checkPopup(e);
    }

    public void mouseReleased(MouseEvent e) {
      checkPopup(e);
    }
}
  
public void checkPopup(MouseEvent e) {
    
       popup.show(CharacterChoice.this, e.getX(), e.getY());
}
  
  class PopupPrintListener implements PopupMenuListener {
    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
      System.out.println("Popup menu will be visible!");
    }

    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
      System.out.println("Popup menu will be invisible!");
    }

    public void popupMenuCanceled(PopupMenuEvent e) {
      System.out.println("Popup menu is hidden!");
    }
  }

  
}

           