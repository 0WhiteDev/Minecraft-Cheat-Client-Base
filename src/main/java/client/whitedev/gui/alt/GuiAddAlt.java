package client.whitedev.gui.alt;

import client.whitedev.mods.altmanager.Alt;
import client.whitedev.mods.altmanager.AltManager;
import client.whitedev.mods.altmanager.PasswordField;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.net.Proxy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GuiAddAlt extends GuiScreen {
    private final GuiAltManager manager;
    private PasswordField password;
    private String status;
    private GuiTextField username;
    public String path;
    String home2 = System.getProperty("user.home");
    String path2 = home2 + "\\AppData\\Roaming\\.minecraft\\alts.txt";

    public GuiAddAlt(GuiAltManager manager) {
        this.status = EnumChatFormatting.GRAY + "Idle...";
        this.manager = manager;
    }


    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                GuiAddAlt.AddAltThread login = new GuiAddAlt.AddAltThread(this.username.getText(), this.password.getText());
                login.start();
                FileWriter file = new FileWriter(path2, true);
                BufferedWriter out = new BufferedWriter(file);
                out.write("\n" + this.username.getText() + ":" + this.password.getText());
                out.close();
                break;
            case 2:
                addingAlts();
                break;
            case 1:
                this.mc.displayGuiScreen(this.manager);
        }

    }

    public void addingAlts(){
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION) { path = chooser.getSelectedFile().getAbsolutePath(); }
        if(path != null) {
            File txt = new File(path);
            try {
                BufferedReader reader = new BufferedReader(new FileReader(txt));
                String line = "";
                String email;
                String pass;
                while ((line = reader.readLine()) != null) {
                    Matcher m = Pattern.compile(":").matcher(line);
                    while (m.find()) {
                        String line_ = line;
                        String processed = line_.replace(":", " ");
                        int index = -1;
                        for (int i = 0; i < processed.length(); i++) {
                            if (Character.isWhitespace(processed.charAt(i))) {
                                index = i + 1;
                                break;
                            }
                        }
                        email = processed.substring(0, index - 1);
                        pass = processed.substring(index);
                        GuiAddAlt.AddAltThread login2 = new GuiAddAlt.AddAltThread(email, pass);
                        login2.start();
                        FileWriter file = new FileWriter(path2, true);
                        BufferedWriter out = new BufferedWriter(file);
                        out.write("\n" + email + ":" + pass);
                        out.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void drawScreen(int i2, int j2, float f2) {
        new GuiAddAlt(new GuiAltManager()).loadAlts();
        this.drawDefaultBackground();
        this.username.drawTextBox();
        this.password.drawTextBox();
        drawCenteredString(this.fontRendererObj, "Add Alt", width / 2, 20, -1);
        if (this.username.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "Username / E-Mail", width / 2 - 96, 66, -7829368);
        }

        if (this.password.getText().isEmpty()) {
            this.drawString(this.mc.fontRendererObj, "Password", width / 2 - 96, 106, -7829368);
        }

        drawCenteredString(this.fontRendererObj, this.status, width / 2, 30, -1);
        super.drawScreen(i2, j2, f2);
    }

    public void loadAlts(){
        if(!GuiAltManager.loaded) {
            GuiAltManager.loaded = true;
            String home = System.getProperty("user.home");
            String path = home + "\\AppData\\Roaming\\.minecraft\\alts.txt";
            File txt = new File(path);
            try {
                BufferedReader reader = new BufferedReader(new FileReader(txt));
                String line = "";
                String email;
                String pass;
                while ((line = reader.readLine()) != null) {
                    Matcher m = Pattern.compile(":").matcher(line);
                    while (m.find()) {
                        String line_ = line;
                        String processed = line_.replace(":", " ");
                        int index = -1;
                        for (int i = 0; i < processed.length(); i++) {
                            if (Character.isWhitespace(processed.charAt(i))) {
                                index = i + 1;
                                break;
                            }
                        }
                        email = processed.substring(0, index - 1);
                        pass = processed.substring(index);
                        GuiAddAlt.AddAltThread login2 = new GuiAddAlt.AddAltThread(email, pass);
                        login2.start();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(9999, 5, 999999, 90, 20, ""));
        this.buttonList.add(new GuiButton(0, width / 2 - 100, height / 4 + 92 + 12, "Login"));
        this.buttonList.add(new GuiButton(1, width / 2 - 100, height / 4 + 116 + 12, "Back"));
        this.buttonList.add(new GuiButton(2, width / 2 - 100, height / 4 + 140 + 12, "Add Alts from PC"));
        this.username = new GuiTextField(this.eventButton, this.mc.fontRendererObj, width / 2 - 100, 60, 200, 20);
        this.password = new PasswordField(this.mc.fontRendererObj, width / 2 - 100, 100, 200, 20);
    }

    protected void keyTyped(char par1, int par2) throws IOException {
        this.username.textboxKeyTyped(par1, par2);
        this.password.textboxKeyTyped(par1, par2);
        if (par1 == '\t' && (this.username.isFocused() || this.password.isFocused())) {
            this.username.setFocused(!this.username.isFocused());
            this.password.setFocused(!this.password.isFocused());
        }

        if (par1 == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }

    }

    protected void mouseClicked(int par1, int par2, int par3) {
        try {
            super.mouseClicked(par1, par2, par3);
        } catch (IOException var5) {
            var5.printStackTrace();
        }

        this.username.mouseClicked(par1, par2, par3);
        this.password.mouseClicked(par1, par2, par3);
    }

    private class AddAltThread extends Thread {
        private final String password;
        private final String username;

        public AddAltThread(String username, String password) {
            this.username = username;
            this.password = password;
            GuiAddAlt.this.status = EnumChatFormatting.GRAY + "Idle...";
        }

        private void checkAndAddAlt(String username, String password) throws IOException {
            YggdrasilAuthenticationService service = new YggdrasilAuthenticationService(Proxy.NO_PROXY, "");
            YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) service.createUserAuthentication(Agent.MINECRAFT);
            auth.setUsername(username);
            auth.setPassword(password);

            try {
                auth.logIn();
                AltManager.registry.add(new Alt(username, password, auth.getSelectedProfile().getName()));
                GuiAddAlt.this.status = "Alt added. (" + username + ")";
            } catch (AuthenticationException var6) {
                GuiAddAlt.this.status = EnumChatFormatting.RED + "Alt failed!";
                var6.printStackTrace();
            }

        }

        public void run() {
            if (this.password.equals("")) {
                AltManager.registry.add(new Alt(this.username, ""));
                GuiAddAlt.this.status = EnumChatFormatting.GREEN + "Alt added. (" + this.username + " - offline name)";
            } else {
                GuiAddAlt.this.status = EnumChatFormatting.YELLOW + "Trying alt...";

                try {
                    this.checkAndAddAlt(this.username, this.password);
                } catch (IOException var2) {
                    var2.printStackTrace();
                }

            }
        }
    }
}
