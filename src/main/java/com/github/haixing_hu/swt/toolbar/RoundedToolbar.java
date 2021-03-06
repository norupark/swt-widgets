/*******************************************************************************
 * Copyright (c) 2012 Laurent CARON. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Laurent CARON (laurent.caron at gmail dot com) - initial API and implementation
 *  Haixing Hu (https://github.com/Haixing-Hu/)  - Modification for personal use.
 *******************************************************************************/
package com.github.haixing_hu.swt.toolbar;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Widget;

import com.github.haixing_hu.swt.utils.AdvancedPath;
import com.github.haixing_hu.swt.utils.SWTResourceManager;

/**
 * Instances of this class support the layout of selectable rounded tool bar
 * items.
 * <p>
 * The item children that may be added to instances of this class must be of
 * type <code>RoundedToolItem</code>.
 * </p>
 * <p>
 * <dl>
 * <dt><b>Styles:</b></dt>
 * <dd>NONE</dd>
 * <dt><b>Events:</b></dt>
 * <dd>(none)</dd>
 * </dl>
 *
 * @see <a href="http://www.eclipse.org/swt/snippets/#toolbar">ToolBar, ToolItem
 *      snippets</a>
 */
public class RoundedToolbar extends Canvas {

  public static final int DEFAULT_CORNER_RADIUS = 8;

  public static final RGB DEFAULT_START_GRADIENT_COLOR = new RGB(245, 245, 245);

  public static final RGB DEFAULT_END_GRADIENT_COLOR = new RGB(185, 185, 185);

  public static final RGB DEFAULT_BORDER_COLOR = new RGB(66, 66, 66);

  private final List<RoundedToolItem> items;
  private boolean multiSelection;
  private int cornerRadius;
  private final Color startGradientColor;
  private final Color endGradientColor;
  private final Color borderColor;

  /**
   * Constructs a new instance of this class given its parent and a style value
   * describing its behavior and appearance.
   * <p>
   * The style value is either one of the style constants defined in class
   * <code>SWT</code> which is applicable to instances of this class, or must be
   * built by <em>bitwise OR</em>'ing together (that is, using the
   * <code>int</code> "|" operator) two or more of those <code>SWT</code> style
   * constants. The class description lists the style constants that are
   * applicable to the class. Style bits are also inherited from superclasses.
   * </p>
   *
   * @param parent
   *          a composite control which will be the parent of the new instance
   *          (cannot be null)
   * @param style
   *          the style of control to construct
   *
   * @exception IllegalArgumentException
   *              <ul>
   *              <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
   *              </ul>
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the parent</li>
   *              <li>ERROR_INVALID_SUBCLASS - if this class is not an allowed
   *              subclass</li>
   *              </ul>
   *
   * @see Widget#getStyle()
   */
  public RoundedToolbar(final Composite parent, final int style) {
    this(parent, style, DEFAULT_CORNER_RADIUS, DEFAULT_START_GRADIENT_COLOR,
        DEFAULT_END_GRADIENT_COLOR, DEFAULT_BORDER_COLOR);
  }


  /**
   * Constructs a new instance of this class given its parent and a style value
   * describing its behavior and appearance.
   * <p>
   * The style value is either one of the style constants defined in class
   * <code>SWT</code> which is applicable to instances of this class, or must be
   * built by <em>bitwise OR</em>'ing together (that is, using the
   * <code>int</code> "|" operator) two or more of those <code>SWT</code> style
   * constants. The class description lists the style constants that are
   * applicable to the class. Style bits are also inherited from superclasses.
   * </p>
   *
   * @param parent
   *          a composite control which will be the parent of the new instance
   *          (cannot be null)
   * @param style
   *          the style of control to construct.
   * @param cornerRadius
   *          the radius of the rounded corner.
   * @exception IllegalArgumentException
   *              <ul>
   *              <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
   *              </ul>
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the parent</li>
   *              <li>ERROR_INVALID_SUBCLASS - if this class is not an allowed
   *              subclass</li>
   *              </ul>
   *
   * @see Widget#getStyle()
   */
  public RoundedToolbar(final Composite parent, final int style, int cornerRadius) {
    this(parent, style, cornerRadius, DEFAULT_START_GRADIENT_COLOR,
        DEFAULT_END_GRADIENT_COLOR, DEFAULT_BORDER_COLOR);
  }

  /**
   * Constructs a new instance of this class given its parent, a style value
   * describing its behavior and appearance and colors to specify the start and
   * end gradient of the rounded corner
   * <p>
   * The style value is either one of the style constants defined in class
   * <code>SWT</code> which is applicable to instances of this class, or must be
   * built by <em>bitwise OR</em>'ing together (that is, using the
   * <code>int</code> "|" operator) two or more of those <code>SWT</code> style
   * constants. The class description lists the style constants that are
   * applicable to the class. Style bits are also inherited from superclasses.
   * </p>
   *
   * @param parent
   *          a composite control which will be the parent of the new instance
   *          (cannot be null)
   * @param style
   *          the style of control to construct.
   * @param cornerRadius
   *          the radius of the rounded corner.
   * @param startGradientColor
   *          the RGB of the gradient start color.
   * @param endGradientColor
   *          the RGB of the gradient end color.
   * @param borderColor
   *          the RGB of the border color.
   * @exception IllegalArgumentException
   *              <ul>
   *              <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
   *              </ul>
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the parent</li>
   *              <li>ERROR_INVALID_SUBCLASS - if this class is not an allowed
   *              subclass</li>
   *              </ul>
   *
   * @see Widget#getStyle()
   */
  public RoundedToolbar(final Composite parent, final int style, int cornerRadius,
      RGB startGradientColor, RGB endGradientColor, RGB borderColor) {
    super(parent, style | SWT.DOUBLE_BUFFERED);
    items = new ArrayList<RoundedToolItem>();
    this.cornerRadius = cornerRadius;
    this.startGradientColor = SWTResourceManager.getColor(startGradientColor);
    this.endGradientColor = SWTResourceManager.getColor(endGradientColor);
    this.borderColor = SWTResourceManager.getColor(borderColor);
    addListeners();
  }

  private void addListeners() {
    addListener(SWT.MouseUp, new Listener() {
      @Override
      public void handleEvent(final Event event) {
        for (final RoundedToolItem item : items) {
          if (item.getBounds().contains(event.x, event.y)) {
            if (! multiSelection) {
              applyRadioBehaviour(item);
            }
            item.setSelection(! item.getSelection());
            item.fireSelectionEvent();
            redraw();
            update();
            return;
          }
        }
      }

      private void applyRadioBehaviour(final RoundedToolItem selectedItem) {
        for (final RoundedToolItem item : items) {
          if (! item.equals(selectedItem)) {
            item.setSelection(false);
            item.fireSelectionEvent();
          }
        }
      }
    });

    addListener(SWT.MouseHover, new Listener() {
      @Override
      public void handleEvent(final Event event) {
        for (final RoundedToolItem item : items) {
          if (item.getBounds().contains(event.x, event.y)) {
            setToolTipText(item.getTooltipText() == null ? "" : item
                .getTooltipText());
            return;
          }
        }
      }
    });

    addPaintListener(new PaintListener() {
      @Override
      public void paintControl(final PaintEvent e) {
        RoundedToolbar.this.paintControl(e);
      }
    });
  }

  /**
   * Add an item to the toolbar
   *
   * @param roundedToolItem
   *          roundedToolItem to add
   */
  void addItem(final RoundedToolItem roundedToolItem) {
    items.add(roundedToolItem);
  }

  /**
   * @see org.eclipse.swt.widgets.Composite#computeSize(int, int, boolean)
   */
  @Override
  public Point computeSize(final int wHint, final int hHint,
      final boolean changed) {
    checkWidget();
    int width = 0, height = 0;
    for (final RoundedToolItem item : items) {
      width += item.getWidth();
      height = Math.max(height, item.getHeight());
    }
    return new Point(Math.max(width, wHint), Math.max(height, hHint));
  }

  /**
   * Returns the item at the given, zero-relative index in the receiver. Throws
   * an exception if the index is out of range.
   *
   * @param index
   *          the index of the item to return
   * @return the item at the given index
   *
   * @exception IllegalArgumentException
   *              <ul>
   *              <li>ERROR_INVALID_RANGE - if the index is not between 0 and
   *              the number of elements in the list minus 1 (inclusive)</li>
   *              </ul>
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   */
  public RoundedToolItem getItem(final int index) {
    checkWidget();
    if ((index < 0) || (index > items.size())) {
      SWT.error(SWT.ERROR_INVALID_ARGUMENT);
    }
    return items.get(index);
  }

  /**
   * Returns the item at the given point in the receiver or null if no such item
   * exists. The point is in the coordinate system of the receiver.
   *
   * @param point
   *          the point used to locate the item
   * @return the item at the given point
   *
   * @exception IllegalArgumentException
   *              <ul>
   *              <li>ERROR_NULL_ARGUMENT - if the point is null</li>
   *              </ul>
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   */
  public RoundedToolItem getItem(final Point point) {
    checkWidget();
    for (final RoundedToolItem item : items) {
      if (item.getBounds().contains(point)) {
        return item;
      }
    }
    return null;
  }

  /**
   * Returns the number of items contained in the receiver.
   *
   * @return the number of items
   *
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   */
  public int getItemCount() {
    return items.size();
  }

  /**
   * Returns an array of <code>RoundedToolItem</code>s which are the items in
   * the receiver.
   * <p>
   * Note: This is not the actual structure used by the receiver to maintain its
   * list of items, so modifying the array will not affect the receiver.
   * </p>
   *
   * @return the items in the receiver
   *
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   */
  public RoundedToolItem[] getItems() {
    checkWidget();
    return items.toArray(new RoundedToolItem[items.size()]);
  }

  /**
   * Searches the receiver's list starting at the first item (index 0) until an
   * item is found that is equal to the argument, and returns the index of that
   * item. If no item is found, returns -1.
   *
   * @param item
   *          the search item
   * @return the index of the item
   *
   * @exception IllegalArgumentException
   *              <ul>
   *              <li>ERROR_NULL_ARGUMENT - if the tool item is null</li>
   *              <li>ERROR_INVALID_ARGUMENT - if the tool item has been
   *              disposed</li>
   *              </ul>
   * @exception SWTException
   *              <ul>
   *              <li>ERROR_WIDGET_DISPOSED - if the receiver has been disposed</li>
   *              <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
   *              thread that created the receiver</li>
   *              </ul>
   */
  public int indexOf(final RoundedToolItem item) {
    checkWidget();
    return items.indexOf(item);
  }

  /**
   * Paint the component
   *
   * @param e
   *          event
   */
  protected void paintControl(final PaintEvent e) {
    final GC gc = e.gc;
    gc.setAdvanced(true);
    gc.setAntialias(SWT.ON);

    final int width = getSize().x;
    final int height = getSize().y;

    drawBorders(gc, width, height);
    final Iterator<RoundedToolItem> it = items.iterator();
    int x = 0;
    while (it.hasNext()) {
      final RoundedToolItem item = it.next();
      item.drawButton(gc, x, height, ! it.hasNext());
      x += item.getWidth();
    }
  }

  private void drawBorders(final GC gc, final int width, final int height) {
    final AdvancedPath path = new AdvancedPath(getDisplay());
    path.addRoundRectangle(0, 0, width, height, cornerRadius, cornerRadius);
    gc.setClipping(path);

    gc.setForeground(startGradientColor);
    gc.setBackground(endGradientColor);
    gc.fillGradientRectangle(0, 0, width, height, true);
    gc.setForeground(borderColor);
    gc.drawRoundRectangle(0, 0, width - 1, height - 1, cornerRadius,
        cornerRadius);
    gc.setClipping((Rectangle) null);
  }

  /**
   * Add an item to the toolbar
   *
   * @param roundedToolItem
   *          roundedToolItem to add
   */
  void removeItem(final RoundedToolItem roundedToolItem) {
    checkWidget();
    items.remove(roundedToolItem);
  }

  /**
   * @return the corner radius
   */
  public int getCornerRadius() {
    return cornerRadius;
  }


  /**
   * @param cornerRadius
   *          new corner radius
   */
  public void setCornerRadius(final int cornerRadius) {
    this.cornerRadius = cornerRadius;
  }

  /**
   * Gets the startGradientColor.
   *
   * @return the startGradientColor.
   */
  public Color getStartGradientColor() {
    return startGradientColor;
  }

  /**
   * Gets the endGradientColor.
   *
   * @return the endGradientColor.
   */
  public Color getEndGradientColor() {
    return endGradientColor;
  }

  /**
   * Gets the borderColor.
   *
   * @return the borderColor.
   */
  public Color getBorderColor() {
    return borderColor;
  }

  /**
   * @return <code>true</code> if the toolbar is in multiselection mode,
   *         <code>false</code> otherwise
   */
  public boolean isMultiselection() {
    return multiSelection;
  }


  /**
   * @param multiSelection
   *          new value of the multi selection flag
   */
  public void setMultiselection(final boolean multiSelection) {
    this.multiSelection = multiSelection;
  }

}
