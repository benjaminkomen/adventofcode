package com.benjamin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day1Test {
	private final String input = "L1, L3, L5, L3, R1, L4, L5, R1, R3, L5, R1, L3, L2, L3, R2, R2, L3, L3, R1, L2, R1, L3, L2, R4, R2, L5, R4, L5, R4, L2, R3, L2, R4, R1, L5, L4, R1, L2, R3, R1, R2, L4, R1, L2, R3, L2, L3, R5, L192, R4, L5, R4, L1, R4, L4, R2, L5, R45, L2, L5, R4, R5, L3, R5, R77, R2, R5, L5, R1, R4, L4, L4, R2, L4, L1, R191, R1, L1, L2, L2, L4, L3, R1, L3, R1, R5, R3, L1, L4, L2, L3, L1, L1, R5, L4, R1, L3, R1, L2, R1, R4, R5, L4, L2, R4, R5, L1, L2, R3, L4, R2, R2, R3, L2, L3, L5, R3, R1, L4, L3, R4, R2, R2, R2, R1, L4, R4, R1, R2, R1, L2, L2, R4, L1, L2, R3, L3, L5, L4, R4, L3, L1, L5, L3, L5, R5, L5, L4, L2, R1, L2, L4, L2, L4, L1, R4, R4, R5, R1, L4, R2, L4, L2, L4, R2, L4, L1, L2, R1, R4, R3, R2, R2, R5, L1, L2";

	public static void main(String[] args) {

		new Day1Test().go();
	}

	private void go() {

		final List<Action> actions = parseInstructions(input);

		final Position position = new Position(0, 0);

		for (final Action action : actions) {
			action.doAction(position);
		}

		System.out.println("Final location: " + position.getLocation());
	}

	private enum TurnDirection {
		LEFT, RIGHT
	}

	private enum CompassDirection {
		NORTH(0), EAST(1), SOUTH(2), WEST(3);

		private final int ordinal;

		CompassDirection(final int i) {
			this.ordinal = i;
		}

		private static CompassDirection fromOrdinal(final int ordinal) {
			for (final CompassDirection compassDirection : CompassDirection.values()) {
				if (compassDirection.ordinal == ordinal) {
					return compassDirection;
				}
			}

			throw new RuntimeException("" + ordinal);
		}

		public CompassDirection turn(final TurnDirection turnDirection) {
			switch (turnDirection) {

			// java returns negative modulus values, so need to add 4 and do the
			// mod again to ensure they are positive.
			case LEFT:
				return CompassDirection.fromOrdinal((((this.ordinal - 1) % 4) + 4) % 4);
			case RIGHT:
				return CompassDirection.fromOrdinal((((this.ordinal + 1) % 4) + 4) % 4);
			default:
				throw new RuntimeException("" + turnDirection);
			}
		}
	}

	private final class Position {

		private int x;
		private int y;
		private CompassDirection facing;

		private final Set<String> visited = new HashSet<>();
		private boolean foundFirstLocationVisitedTwice = false;

		private Position(final int x, final int y) {
			this.x = x;
			this.y = y;
			this.facing = CompassDirection.NORTH;
		}

		public void turn(final TurnDirection direction) {
			facing = facing.turn(direction);
		}

		public void move(final int distance) {

			for (int i = 0; i < distance; i++) {

				switch (facing) {

				case NORTH:
					y++;
					break;
				case EAST:
					x++;
					break;
				case SOUTH:
					y--;
					break;
				case WEST:
					x--;
					break;
				}

				if (!foundFirstLocationVisitedTwice) {
					final String currentLocation = x + "," + y;
					if (!visited.contains(currentLocation)) {
						visited.add(currentLocation);
					} else {
						foundFirstLocationVisitedTwice = true;
						System.out.println("First location visited twice: " + getLocation());
					}
				}
			}
			System.out.println(" position is x:" + x + ",y:" + y);
		}

		public String getLocation() {
			final int distance = Math.abs(x) + Math.abs(y);
			return "At x=" + x + ", y=" + y + ", facing " + facing + ", distance=" + distance;
		}
	}

	private interface Action {
		void doAction(final Position position);
	}

	private static final class Turn implements Action {

		private final TurnDirection direction;

		private Turn(final TurnDirection direction) {
			this.direction = direction;
		}

		@Override
		public void doAction(final Position position) {
			position.turn(direction);
		}

		@Override
		public String toString() {
			return "Turn " + direction;
		}
	}

	private static final class Move implements Action {

		private final int distance;

		private Move(final int distance) {
			this.distance = distance;
		}

		@Override
		public void doAction(final Position position) {
			position.move(distance);
		}

		@Override
		public String toString() {
			return "Move " + distance;
		}
	}

	private List<Action> parseInstructions(final String input) {
		final List<Action> actions = new ArrayList<>();

		final Pattern pattern = Pattern.compile("([RL])([0-9]+)(?:, )?");

		final Matcher matcher = pattern.matcher(input);

		while (matcher.find()) {
			final String direction = matcher.group(1);
			switch (direction) {
			case "R":
				actions.add(new Turn(TurnDirection.RIGHT));
				break;
			case "L":
				actions.add(new Turn(TurnDirection.LEFT));
				break;
			default:
			}

			final String distance = matcher.group(2);
			actions.add(new Move(Integer.parseInt(distance)));
		}

		return actions;
	}
}
