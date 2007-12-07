/*** preinitBlock ***/
Token $actorSymbol(_data);
Token $actorSymbol(_zero);
int $actorSymbol(_mostRecent);
int $actorSymbol(_phaseLength);
Token $actorSymbol(_outToken);
Token $actorSymbol(_tapItem);
Token $actorSymbol(_dataItem);
Token $actorSymbol(_taps);
/**/

/*** sharedBlock ***/
        int $actorClass(length);
        int $actorClass(inC);
        int $actorClass(phase);
        int $actorClass(dataIndex);
        int $actorClass(tapsIndex);
        int $actorClass(i);
        int $actorClass(bufferIndex);   // for output offset in a single firing.
        int $actorClass(inputIndex);        // for input offset.
/**/

/*** initBlock0 ***/
    $actorSymbol(_taps) = $ref(taps);
/**/

/*** initBlock ***/    
        $actorSymbol(_zero) = $tokenFunc(Array_get($actorSymbol(_taps), 0)::zero());
        
        $actorSymbol(_phaseLength) = $actorSymbol(_taps).payload.Array->size / $val(interpolation);
        
        if (($actorSymbol(_taps).payload.Array->size % $val(interpolation)) != 0) {
            $actorSymbol(_phaseLength)++;
        }
        
        // Create new data array and initialize index into it.
        // Avoid losing the data if possible.
        // NOTE: If the filter length increases, then it is impossible
        // to correctly initialize the delay line to contain previously
        // seen data, because that data has not been saved.
        $actorClass(length) = $actorSymbol(_phaseLength) + $val(decimation);
        
        $actorSymbol(_data) = $new(Array($actorClass(length), 0));
        
        for ($actorClass(i) = 0; $actorClass(i) < $actorClass(length); $actorClass(i)++) {
            Array_set($actorSymbol(_data), $actorClass(i), $actorSymbol(_zero));
        }
        $actorSymbol(_mostRecent) = $actorSymbol(_phaseLength);
/**/



/*** reinitBlock ***/
$actorSymbol(_zero) = $tokenFunc(Array_get($actorSymbol(_taps), 0)::zero());

$actorSymbol(_phaseLength) = $actorSymbol(_taps).payload.Array->size / $val(interpolation);

if (($actorSymbol(_taps).payload.Array->size % $val(interpolation)) != 0) {
    $actorSymbol(_phaseLength)++;
}

// Create new data array and initialize index into it.
// Avoid losing the data if possible.
// NOTE: If the filter length increases, then it is impossible
// to correctly initialize the delay line to contain previously
// seen data, because that data has not been saved.
$actorClass(length) = $actorSymbol(_phaseLength) + $val(decimation);

if ($actorSymbol(_data).payload.Array->size != $actorClass(length)) {
        $actorSymbol(_data).payload.Array->elements = (Token*) realloc($actorSymbol(_data).payload.Array->elements, $actorClass(length) * sizeof(Token));
        for ($actorClass(i) = $actorSymbol(_data).payload.Array->size; $actorClass(i) < $actorClass(length); $actorClass(i)++) {
            Array_set($actorSymbol(_data), $actorClass(i), $actorSymbol(_zero));
        }
        $actorSymbol(_data).payload.Array->size = $actorClass(length);
        $actorSymbol(_mostRecent) = $actorSymbol(_phaseLength);
}

/**/




/*** fireBlock0 ***/
$actorClass(bufferIndex) = 0;
$actorClass(inputIndex) = 0;
/**/

/*** fireBlock ***/
// Phase keeps track of which phase of the filter coefficients
// are used. Starting phase depends on the $val(decimationPhase) value.
$actorClass(phase) = $val(decimation) - $val(decimationPhase) - 1;

// Transfer decimation inputs to _data[]
for ($actorClass(inC) = 1; $actorClass(inC) <= $val(decimation); $actorClass(inC)++) {
    if (--$actorSymbol(_mostRecent) < 0) {
        $actorSymbol(_mostRecent) = $actorClass(length) - 1;
    }

    // Note explicit type conversion, which is required to generate
    // code.
    Array_set($actorSymbol(_data), $actorSymbol(_mostRecent), $ref((Token) input, $actorClass(inputIndex)++));
}

// Interpolate once for each input consumed
for ($actorClass(inC) = 1; $actorClass(inC) <= $val(decimation); $actorClass(inC)++) {
    // Produce however many outputs are required
    // for each input consumed
    while ($actorClass(phase) < $val(interpolation)) {
        $actorSymbol(_outToken) = $actorSymbol(_zero);

        // Compute the inner product.
        for ($actorClass(i) = 0; $actorClass(i) < $actorSymbol(_phaseLength); $actorClass(i)++) {
            $actorClass(tapsIndex) = ($actorClass(i) * $val(interpolation)) + $actorClass(phase);

            $actorClass(dataIndex) = (($actorSymbol(_mostRecent) + $val(decimation)) - $actorClass(inC) + $actorClass(i)) % ($actorClass(length));

            if ($actorClass(tapsIndex) < $actorSymbol(_taps).payload.Array->size) {
                $actorSymbol(_tapItem) = Array_get($actorSymbol(_taps), $actorClass(tapsIndex));
                $actorSymbol(_dataItem) = Array_get($actorSymbol(_data), $actorClass(dataIndex));
                $actorSymbol(_dataItem) = $tokenFunc($actorSymbol(_tapItem)::multiply($actorSymbol(_dataItem)));
                $actorSymbol(_outToken) = $tokenFunc($actorSymbol(_outToken)::add($actorSymbol(_dataItem)));
            }

            // else assume tap is zero, so do nothing.
        }

        $ref(output, ($actorClass(bufferIndex)++)) = $actorSymbol(_outToken).payload.$cgType(output);
        $actorClass(phase) += $val(decimation);
    }

    $actorClass(phase) -= $val(interpolation);
}
/**/

/*** wrapupBlock ***/
//Array_delete($actorSymbol(_data));
/**/
