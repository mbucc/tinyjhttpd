# September 9, 2021
# Extract summary statistics from Java's native memory diff output.

BEGIN {
	fmt = "%10s %10s   %s\n";
}

/^ *- +/ {
	# Example line:
	#    -                 Java Heap (reserved=10240KB, committed=10240KB)
	n = split($0, parts, "\(")
	namepart = parts[1]
	p = match(namepart, "[A-Z][^ ]+( [A-Z][^ ]+)?")
	name = substr(namepart, p, RLENGTH)

	mempart = parts[2]
	n = split(mempart, memparts, "=")
	committedpart = memparts[3]
	p = match(committedpart, "[1-9][^K]+KB")
	committed = substr(committedpart, p, RLENGTH)
	p = match(committedpart, "[-+][1-9][^K]*KB")
	if (RLENGTH > -1) {
		delta = substr(committedpart, p, RLENGTH)
	} else {
		delta = 0
	}
	printf fmt, committed, delta, name
}

/^Total/ {
	committed = $4
	sub("committed=", "", committed)
	delta = $5
	printf fmt, committed, delta, "Total"
}
