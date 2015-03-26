from cStringIO import StringIO

# Output example:
#
# skin.png
# size: 256,128
# format: RGBA8888
# filter: Linear,Linear
# repeat: none
# check-off
#   rotate: false
#   xy: 11, 5
#   size: 14, 14
#   orig: 14, 14
#   offset: 0, 0
#   index: -1
def create_atlas(texture, size, grid_size, region_names):
	grid_indicator_size = 1

	result = StringIO()

	indent = 0

	def value(v):
		result.write(v + "\n")

	def entry(key, value):
		result.write(" " * indent + key + ": " + str(value) + "\n")

	value(texture)
	entry("size", str(size[0]) + "," + str(size[1]))
	entry("format", "RGBA8888")

	entry("filter", "Linear,Linear")
	entry("repeat", "none")

	x, y = (0, 0)
	indent = 2
	for region in region_names:
		result.write(region + "\n")
		entry("rotate", "false")
		entry("xy", str(x * grid_size) + ", " + str(y * grid_size))
		entry("size", str(grid_size) + ", " + str(grid_size))
		entry("orig", str(x * grid_size) + ", " + str(y * grid_size))
		entry("offset", "0, 0")
		entry("index", "-1")

		x = (x + 1) % (size[0] / grid_size)

		if x == 0:
			y = (y + 1) % (size[1] / grid_size)

	return result

atlas = create_atlas("ui_icons.png", [85, 85], 21, ["alignv", "alignh", "file_open", "file_save", "bg_up", "bg_over", "bg_down"])

print atlas.getvalue()