# Converts bitmap fonts from XML format (GHL) to .fnt format

from xml.dom.minidom import parse
from StringIO import StringIO

dom = parse("e:/src/dont-stop-running/core/assets/ui/marke_eigenbau_normal_8.xml")

root = dom.childNodes[0]


def find_node(root, node_name):
    for node in root.childNodes:
        if node.nodeName == node_name:
            return node
    return None


def find_attr(node, attr_name, default=None):
    for index in range(0, node.attributes.length):
        attribute = node.attributes.item(index)
        if attribute.name == attr_name:
            return attribute.value
    return default


def convert_xml(root_node):
    description = find_node(root_node, "description")
    metrics = find_node(root_node, "metrics")
    texture = find_node(root_node, "texture")
    chars = find_node(root_node, "chars")

    info = {"face": find_attr(description, "family"),
            "size": find_attr(description, "size"),
            "bold": find_attr(description, "bold", 0),
            "italic": find_attr(description, "italic", 0),
            "charset": "\"\"",
            "unicode": "1",
            "stretchH": "100",
            "smooth": "0",
            "aa": "0",
            "padding": "0,0,0,0",
            "spacing": "1,1",
            "outline": "0"}

    common = {"lineHeight": find_attr(metrics, "height"),
              "base": find_attr(metrics, "ascender"),
              "scaleW": find_attr(texture, "width"),
              "scaleH": find_attr(texture, "height"),
              "pages": "1",
              "packed": "0",
              "alphaChnl": "1",
              "redChnl": "0",
              "greenChnl": "0",
              "blueChnl": "0"}

    source_file = find_attr(texture, "file")

    chars_count = chars.childNodes.length

    result = StringIO()

    def pval(dictionary, key):
        return str(key) + "=" + str(dictionary[key]) + " "

    def pdict(dictionary, keys=None):
        val = StringIO()
        for key in (keys if keys is not None else dictionary):
            val.write(pval(dictionary, key))
        return val.getvalue()

    info_keys = ["face", "size", "bold", "italic", "charset", "unicode", "stretchH", "smooth", "aa", "padding",
                 "spacing", "outline"]
    result.write("info " + pdict(info, info_keys) + "\n")

    common_keys = ["lineHeight", "base", "scaleW", "scaleH", "pages", "packed", "alphaChnl", "redChnl", "greenChnl",
                   "blueChnl"]
    result.write("common " + pdict(common, common_keys) + "\n")

    result.write("page id=0 file=\"" + str(source_file) + "\"\n")

    result.write("chars count=" + str(chars_count) + "\n")

    char_keys = ["id", "x", "y", "width", "height", "xoffset", "yoffset", "xadvance", "page", "chnl"]

    for charNode in chars.childNodes:
        if charNode.attributes is not None:
            char = {"id": ord(find_attr(charNode, "id")),
                    "x": find_attr(charNode, "rect").split(" ")[0],
                    "y": find_attr(charNode, "rect").split(" ")[1],
                    "width": find_attr(charNode, "rect").split(" ")[2],
                    "height": find_attr(charNode, "rect").split(" ")[3],
                    "xoffset": find_attr(charNode, "offset").split(" ")[0],
                    "yoffset": int(find_attr(metrics, "height")) - int(find_attr(charNode, "offset").split(" ")[1]),
                    "xadvance": find_attr(charNode, "advance"),
                    "page": "0",
                    "chnl": "15"
            }
            result.write("char " + pdict(char, char_keys) + "\n")

    return result.getvalue()


print convert_xml(root)
