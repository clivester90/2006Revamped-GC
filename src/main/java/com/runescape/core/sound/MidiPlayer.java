package com.runescape.core.sound;/* com.Class56_Sub1_Sub1 - Decompiled by JODE
 * Visit http://jode.sourceforge.net/
 */
import com.Game;

import java.io.ByteArrayInputStream;

import javax.sound.midi.*;

public final class MidiPlayer extends SomethingForMusic implements Receiver
{
    private static Receiver receiver = null;
    private static Sequencer sequencer = null;
    
    public void method827(int volume, byte[] data, int i_0_, boolean loop) {
    	if (sequencer != null) {
    		try {
    			Sequence sequence = MidiSystem.getSequence(new ByteArrayInputStream(data));
				sequencer.setSequence(sequence);
    			sequencer.setLoopCount(!loop ? 0 : -1);
    			setVolume(0, volume, -1L);
    			sequencer.start();
    		} catch (Exception exception) {
    			/* empty */
    		}
    	}
    }
    
    public void method833() {
		if (sequencer != null) {
		    sequencer.stop();
			setTick(-1L);
		}
    }
    
    MidiPlayer() {
		try {
		    receiver = MidiSystem.getReceiver();
		    sequencer = MidiSystem.getSequencer(false);
		    sequencer.getTransmitter().setReceiver(this);
		    sequencer.open();
		    setTick(-1L);
		} catch (Exception exception) {
		    Game.method790();
		}
    }
    
    public void method828() {
    	if (sequencer != null) {
    		sequencer.close();
    		sequencer = null;
    	}
    	if (receiver != null) {
    		receiver.close();
    		receiver = null;
    	}
    }

	@Override
	public void send(MidiMessage message, long tick) {
		byte[] data = message.getMessage();
		if (data.length < 3 || !check(data[0], data[1], data[2], tick))
			receiver.send(message, tick);
	}

	public void close() {
	/* empty */
    }
    
    public void method831(int i) {
		if (sequencer != null) {
		    method840(i, -1L);
		}
    }

	public synchronized void method830(int i, int i_2_) {
    	if (sequencer != null) {
    		setVolume(i_2_, i, -1L);
    	}
    }

	public static void setVolume(int i, int i_1_, long l) {
		i_1_ = (int) ((double) i_1_ * Math.pow(0.1, (double) i * 5.0E-4) + 0.5);
		if (i_1_ != Game.anInt1401) {
			Game.anInt1401 = i_1_;
			for (int i_2_ = 0; i_2_ < 16; i_2_++) {
				int i_3_ = method844(i_2_);
				sendMessage(i_2_ + 176, 7, i_3_ >> 7, l);
				sendMessage(i_2_ + 176, 39, i_3_ & 0x7f, l);
			}
		}
	}

	public static boolean check(int i, int i_6_, int i_7_, long l) {
		if ((i & 0xf0) == 176) {
			if (i_6_ == 121) {
				sendMessage(i, i_6_, i_7_, l);
				int i_8_ = i & 0xf;
				Game.channels[i_8_] = 12800;
				int i_9_ = method844(i_8_);
				sendMessage(i, 7, i_9_ >> 7, l);
				sendMessage(i, 39, i_9_ & 0x7f, l);
				return true;
			}
			if (i_6_ == 7 || i_6_ == 39) {
				int i_10_ = i & 0xf;
				if (i_6_ == 7)
					Game.channels[i_10_] = (Game.channels[i_10_] & 0x7f) + (i_7_ << 7);
				else
					Game.channels[i_10_] = (Game.channels[i_10_] & 0x3f80) + i_7_;
				int i_11_ = method844(i_10_);
				sendMessage(i, 7, i_11_ >> 7, l);
				sendMessage(i, 39, i_11_ & 0x7f, l);
				return true;
			}
		}
		return false;
	}

	public static void setTick(long tick) {
		for (int i = 0; i < 16; i++)
			sendMessage(i + 176, 123, 0, tick);
		for (int i = 0; i < 16; i++)
			sendMessage(i + 176, 120, 0, tick);
		for (int i = 0; i < 16; i++)
			sendMessage(i + 176, 121, 0, tick);
		for (int i = 0; i < 16; i++)
			sendMessage(i + 176, 0, 0, tick);
		for (int i = 0; i < 16; i++)
			sendMessage(i + 176, 32, 0, tick);
		for (int i = 0; i < 16; i++)
			sendMessage(i + 192, 0, 0, tick);
	}

	public static void method840(int i, long l) {
		Game.anInt1401 = i;
		for (int i_21_ = 0; i_21_ < 16; i_21_++)
			Game.channels[i_21_] = 12800;
		for (int i_22_ = 0; i_22_ < 16; i_22_++) {
			int i_23_ = method844(i_22_);
			sendMessage(i_22_ + 176, 7, i_23_ >> 7, l);
			sendMessage(i_22_ + 176, 39, i_23_ & 0x7f, l);
		}
	}

	private static int method844(int i) {
		int i_32_ = Game.channels[i];
		i_32_ = (i_32_ * Game.anInt1401 >> 8) * i_32_;
		return (int) (Math.sqrt((double) i_32_) + 0.5);
	}

	public synchronized static void sendMessage(int i, int i_5_, int i_6_, long l) {
    	try {
    		ShortMessage shortmessage = new ShortMessage();
    		shortmessage.setMessage(i, i_5_, i_6_);
    		receiver.send(shortmessage, l);
    	} catch (InvalidMidiDataException invalidmididataexception) {
    		/* empty */
		}
    }

	public void method832(int i) {
	if (i > -90)
	    method833();
    }
}
