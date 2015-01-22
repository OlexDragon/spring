package jk.web.data.beans;

import java.awt.Dimension;
import java.time.LocalTime;

public class VideoProperties {

	private LocalTime duration;
	private Dimension resolution;

	public LocalTime getDuration() {
		return duration;
	}
	public void setDuration(LocalTime duration) {
		this.duration = duration;
	}
	public Dimension getResolution() {
		return resolution;
	}
	public void setResolution(Dimension resolution) {
		this.resolution = resolution;
	}
	@Override
	public String toString() {
		return "VideoProperties [duration=" + duration + ", resolution=" + resolution + "]";
	}
}
