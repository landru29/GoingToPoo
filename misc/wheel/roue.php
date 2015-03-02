#!/usr/bin/php
<?php

    function getAngle($alpha) {
        if ($alpha>90) {
        return  90;
        }
            if ($alpha<-90) {
            return -90;
        }
        return $alpha;
    }
    
    function getSvgText($text, $angle) {
        return '<text x="60"  y="150" font-family="Arial Black" text-anchor="middle" transform="matrix(1 0 0 ' . cos($angle * pi() / 180). ' 0 ' . ($angle < 0 ? 87 * (1 + sin((-45-$angle) * pi() / 90)) : 0) . ')"
        font-size="160" style="fill:#444444;">' . $text . '</text>';
    }

    $number = $argv[1];
    $angle = getAngle($argv[2]);

    $numberAfter = ($number>0 ? $number -1 : 9);
    $numberBefore = ($number<9 ? $number +1 : 0);
    
    $angleBefore = getAngle($angle+100);
    $angleAfter = getAngle($angle-100);

?><?xml version="1.0" encoding="UTF-8" standalone="no"?>

<svg width="120" height="200"  viewBox="0 0 120 200"
     xmlns="http://www.w3.org/2000/svg" version="1.2"
     xmlns:xlink="http://www.w3.org/1999/xlink" >

    <defs>
        <linearGradient
            x1="0%" y1="0%"
            x2="0%" y2="100%"
           id="MyGradient">
          <stop
             id="top"
             offset="0"
             style="stop-color:#f1ff1f;stop-opacity:1;" />
          <stop
             id="middle"
             offset="0.5"
             style="stop-color:#f1ff1f;stop-opacity:0.5;" />
          <stop
             style="stop-color:#f1ff1f;stop-opacity:1;"
             offset="1"
             id="bottom" />
        </linearGradient>
    </defs>

    <rect fill="url(#MyGradient)" x="0" y="0" width="120" height="200"/>
    
    <?php echo getSvgText($number, $angle) ?>
    <?php echo getSvgText($numberBefore, $angleBefore) ?>
    <?php echo getSvgText($numberAfter, $angleAfter) ?>

</svg>
