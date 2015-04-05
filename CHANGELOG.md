# Change Log

## Unreleased
### Added
- Support for editing sprites

## [1.2] 2015-04-04
### Added
- A maximum height indicator to the level editor grid to signal the peak of the level (default: 800)
- Support for fullscreen in the level editor (F11)
- Texture picker dialog for the level editor
- Level background editor with support for 5 background layers
- Play forward / reverse buttons to autoscroll the level
- Support for editing any piece of terrain

### Fixed
- The level editor ruler text is now properly scaling with the zoom
- The save / load dialogs are now customized for the editor, which fixes a bug where the save / load dialogs would be shown under the editor.
- Dragging the camera in the level editor no longer glitches when releasing the space key.

## [1.1] 2015-03-28
### Added
- This CHANGELOG
- Support for serialization of generic objects
- Support for saving and loading levels
- Internal logging class
- New pixel font (marke eigenbau) and a bitmap font converter from xml (NGL) to fnt
- FontRepository for quickly adding and searching fonts
- Background grid for the level editor, with ruler!

## [1.0] - 2015-03-27
### Added
- Algorithms and data structures for polygon editing / triangulation
- An atlas viewer for viewing atlas files
- The core component arhitecture
- An initial version of the level editor
- Support for editing polygons inside the level editor
- Initial support for a toolbox
- Initial icon set for the tools
- Debug ui panel for observing debug values
