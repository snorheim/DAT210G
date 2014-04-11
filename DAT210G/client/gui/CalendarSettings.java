package gui;

import java.time.LocalDate;

import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

public class CalendarSettings {
	private static DatePicker fromDatePicker;
	private static DatePicker toDatePicker;

	public CalendarSettings(DatePicker from, DatePicker to) {
		fromDatePicker = from;
		toDatePicker = to;
	}


	public void configFromDatePicker() {
		final Callback<DatePicker, DateCell> limitDatesFrom = 
				new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						if (fromDatePicker.getValue().isAfter(toDatePicker.getValue())) {
							toDatePicker.setValue(fromDatePicker.getValue().plusDays(1));
						}
					}
				};
			}
		};
		fromDatePicker.setDayCellFactory(limitDatesFrom);
	}

	public void configToDatePicker() {
		final Callback<DatePicker, DateCell> limitDatesTo = 
				new Callback<DatePicker, DateCell>() {
			@Override
			public DateCell call(final DatePicker datePicker) {
				return new DateCell() {
					@Override
					public void updateItem(LocalDate item, boolean empty) {
						super.updateItem(item, empty);
						if (item.isBefore(fromDatePicker.getValue().plusDays(1))) {
							setDisable(true);
							setStyle("-fx-background-color: #ffc0cb;");
						} 
					}
				};
			}
		};
		toDatePicker.setDayCellFactory(limitDatesTo);
	}

}
